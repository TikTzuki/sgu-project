import {NgModule} from '@angular/core';

import {LoginComponent} from './login.component';
import {BrowserModule} from '@angular/platform-browser';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    // SharedModule,
    ReactiveFormsModule,
    CommonModule,
    BrowserModule
  ],
  declarations: [LoginComponent],
  exports: [LoginComponent]
})
export class LoginModule {
}
