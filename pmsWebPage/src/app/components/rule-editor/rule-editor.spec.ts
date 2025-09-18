import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleEditor } from './rule-editor';

describe('RuleEditor', () => {
  let component: RuleEditor;
  let fixture: ComponentFixture<RuleEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RuleEditor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RuleEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
