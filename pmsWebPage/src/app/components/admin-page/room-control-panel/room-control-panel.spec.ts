import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomControlPanel } from './room-control-panel';

describe('RoomControlPanel', () => {
  let component: RoomControlPanel;
  let fixture: ComponentFixture<RoomControlPanel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomControlPanel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoomControlPanel);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
