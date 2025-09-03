import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleReader } from './rule-reader';

describe('RuleReader', () => {
  let component: RuleReader;
  let fixture: ComponentFixture<RuleReader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RuleReader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RuleReader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
