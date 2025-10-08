import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservatinCanceler } from './reservatin-canceler';

describe('ReservatinCanceler', () => {
  let component: ReservatinCanceler;
  let fixture: ComponentFixture<ReservatinCanceler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservatinCanceler]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservatinCanceler);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
