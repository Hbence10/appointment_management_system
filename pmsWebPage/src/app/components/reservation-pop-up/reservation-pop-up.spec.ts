import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationPopUp } from './reservation-pop-up';

describe('ReservationPopUp', () => {
  let component: ReservationPopUp;
  let fixture: ComponentFixture<ReservationPopUp>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationPopUp]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationPopUp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
