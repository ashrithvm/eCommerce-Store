import { TestBed } from '@angular/core/testing';

import { CustomPuzzleService } from './custom-puzzle.service';

describe('CustomPuzzleService', () => {
  let service: CustomPuzzleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomPuzzleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
