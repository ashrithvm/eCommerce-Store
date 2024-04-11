import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuzzlePageComponent } from './puzzle-page.component';

describe('PuzzleComponent', () => {
  let component: PuzzlePageComponent;
  let fixture: ComponentFixture<PuzzlePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PuzzlePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PuzzlePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
