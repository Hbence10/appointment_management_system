import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationMakerPage } from './reservation-maker-page';

describe('ReservationMakerPage', () => {
  let component: ReservationMakerPage;
  let fixture: ComponentFixture<ReservationMakerPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationMakerPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationMakerPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
