import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/from';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/share';
import 'rxjs/add/operator/debounceTime';
import {AbstractControl, AsyncValidatorFn, FormGroup} from '@angular/forms';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {IValidationResponse} from "../model/IValidationResponse";

@Injectable()
export class FormValidationService {

  private _subject$ = new ReplaySubject<IValidationResponse>(1);
  private _validationResponse$ = this._subject$.debounceTime(100).share();
  private _oldValue = null;

  constructor() {
    this._subject$.subscribe();
  }

  public get onValidate(): Observable<IValidationResponse> {
    return this._subject$.map(response => response);
  }

  public validateFormOnChange(group: FormGroup, validateFormCallback: (value: any) => Observable<IValidationResponse>) {
    group.valueChanges.subscribe(value => {
      const isChanged = this.isChanged(value, this._oldValue);
      this._oldValue = value;

      if (!isChanged) {
        return;
      }

      this._subject$.next({validations: []});
      this.validateFormGroup(group);

      validateFormCallback(value).subscribe(validationRes => {
        this._subject$.next(validationRes);
        this.validateFormGroup(group);
      });
    });
  }

  private isChanged(newValue, oldValue): boolean {
    if (!newValue) {
      return true;
    }

    return !!Object.keys(newValue).find(key => !oldValue || newValue[key] !== oldValue[key]);
  }

  private validateFormGroup(group: FormGroup) {
    group.updateValueAndValidity({ emitEvent: true, onlySelf: false });

    Object.keys(group.controls).forEach(controlName => {
      group.controls[controlName].updateValueAndValidity({ emitEvent: true, onlySelf: false });
    });
  }

  public createControlAsyncValidator(fieldName: string): AsyncValidatorFn {
    return (control: AbstractControl) => {
      return this._validationResponse$
        .switchMap(validationRes => {
          const errors = validationRes.validations
            .filter(validation => validation.fields.indexOf(fieldName) >= 0)
            .reduce((errorMap, validation) => {
              errorMap[validation.code] = validation.display;
              return errorMap;
            }, {});

          return Observable.from([errors]);
        })
        .first();
    };
  }

  public createGroupAsyncValidator(): AsyncValidatorFn {
    return (control: AbstractControl) => {

      return this._validationResponse$
        .switchMap(validationRes => {
          const errors = validationRes.validations
            .reduce((errorMap, validation) => {
              errorMap[validation.code] = validation.display;
              return errorMap;
            }, {});

          return Observable.from([errors]);
        })
        .first();
    };
  }
}
