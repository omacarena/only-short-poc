import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AsyncValidateFormComponent } from './async-validate-form/async-validate-form.component';
import {BusinessValidationService} from "./services/business-validation.service";

@NgModule({
  declarations: [
    AppComponent,
    AsyncValidateFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule
  ],
  providers: [
    BusinessValidationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
