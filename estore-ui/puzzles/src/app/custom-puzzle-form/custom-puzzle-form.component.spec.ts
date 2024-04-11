import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomPuzzleFormComponent } from './custom-puzzle-form.component';

describe('CustomPuzzleFormComponent', () => {
  let component: CustomPuzzleFormComponent;
  let fixture: ComponentFixture<CustomPuzzleFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomPuzzleFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomPuzzleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
