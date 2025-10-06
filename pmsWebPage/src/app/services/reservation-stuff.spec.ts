import { TestBed } from '@angular/core/testing';

import { ReservationStuff } from './reservation-stuff';

describe('ReservationStuff', () => {
  let service: ReservationStuff;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationStuff);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
