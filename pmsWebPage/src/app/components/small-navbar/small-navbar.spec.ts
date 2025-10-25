import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallNavbar } from './small-navbar';

describe('SmallNavbar', () => {
  let component: SmallNavbar;
  let fixture: ComponentFixture<SmallNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SmallNavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmallNavbar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
