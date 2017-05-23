import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsyncValidateFormComponent } from './async-validate-form.component';

describe('AsyncValidateFormComponent', () => {
  let component: AsyncValidateFormComponent;
  let fixture: ComponentFixture<AsyncValidateFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsyncValidateFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsyncValidateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
