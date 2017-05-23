import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/from';
import {FormValidationService} from "../services/form-validation.service";
import {BusinessValidationService} from "../services/business-validation.service";

@Component({
  selector: 'app-async-validate-form',
  templateUrl: './async-validate-form.component.html',
  styleUrls: ['./async-validate-form.component.css'],
  providers: [FormValidationService]
})
export class AsyncValidateFormComponent implements OnInit {

  personForm: FormGroup;

  constructor(private _formBuilder: FormBuilder,
              private _formValidationService: FormValidationService,
              private _businessValidationService: BusinessValidationService) {
  }

  ngOnInit() {
    this.personForm = this._formBuilder.group({
      firstName: ['', Validators.required, this._formValidationService.createControlAsyncValidator('firstName')],
      lastName: ['', Validators.required, this._formValidationService.createControlAsyncValidator('lastName')],
    }, {
      asyncValidator: this._formValidationService.createGroupAsyncValidator()
    });

    this._formValidationService.validateFormOnChange(this.personForm, value => this._businessValidationService.validateForm(value));
  }
}
