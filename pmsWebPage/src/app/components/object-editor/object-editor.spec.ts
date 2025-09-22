import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjectEditor } from './object-editor';

describe('ObjectEditor', () => {
  let component: ObjectEditor;
  let fixture: ComponentFixture<ObjectEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ObjectEditor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ObjectEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
