import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsEditor } from './details-editor';

describe('DetailsEditor', () => {
  let component: DetailsEditor;
  let fixture: ComponentFixture<DetailsEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsEditor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
