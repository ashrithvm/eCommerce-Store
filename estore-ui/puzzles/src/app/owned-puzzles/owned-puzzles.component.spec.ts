import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnedPuzzlesComponent } from './owned-puzzles.component';

describe('OwnedPuzzlesComponent', () => {
  let component: OwnedPuzzlesComponent;
  let fixture: ComponentFixture<OwnedPuzzlesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnedPuzzlesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OwnedPuzzlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
