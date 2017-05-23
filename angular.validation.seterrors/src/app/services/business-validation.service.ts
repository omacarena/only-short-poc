import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/from';
import 'rxjs/add/operator/map';
import {IValidationResponse} from "../model/IValidationResponse";

@Injectable()
export class BusinessValidationService {

  public validateForm(value: any): Observable<IValidationResponse> {
    return Observable
      .from([value.firstName !== value.lastName])
      .map(valid => valid ?
        {validations: []}
        :
        {
          validations: [
            {
              code: 'sameValue',
              display: 'First name and last name are the same',
              fields: ['firstName', 'lastName']
            }
          ]
        }
      )
      .delay(500);
  }
}
