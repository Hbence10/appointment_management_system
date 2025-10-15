import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryPopup } from './history-popup';

describe('HistoryPopup', () => {
  let component: HistoryPopup;
  let fixture: ComponentFixture<HistoryPopup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryPopup]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryPopup);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
