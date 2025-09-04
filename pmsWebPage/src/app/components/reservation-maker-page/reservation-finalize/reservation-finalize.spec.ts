import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationFinalize } from './reservation-finalize';

describe('ReservationFinalize', () => {
  let component: ReservationFinalize;
  let fixture: ComponentFixture<ReservationFinalize>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationFinalize]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationFinalize);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
