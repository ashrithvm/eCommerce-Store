import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuzzleEditFormComponent } from './puzzle-edit-form.component';

describe('PuzzleEditFormComponent', () => {
  let component: PuzzleEditFormComponent;
  let fixture: ComponentFixture<PuzzleEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PuzzleEditFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PuzzleEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
