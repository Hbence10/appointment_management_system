import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentSelector } from './appointment-selector';

describe('AppointmentSelector', () => {
  let component: AppointmentSelector;
  let fixture: ComponentFixture<AppointmentSelector>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppointmentSelector]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentSelector);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
