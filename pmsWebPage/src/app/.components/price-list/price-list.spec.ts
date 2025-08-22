import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceList } from './price-list';

describe('PriceList', () => {
  let component: PriceList;
  let fixture: ComponentFixture<PriceList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriceList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriceList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
