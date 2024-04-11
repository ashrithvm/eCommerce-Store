package com.puzzlesapi.mapper;

import com.puzzlesapi.model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.model.PuzzleItem;
import com.puzzlesapi.persistence.PuzzleDAO;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("mapper-tier")

public class PuzzleItemMapperTest {

    private PuzzleItemMapper puzzleItemMapper;
    private PuzzleDAO puzzleDAO;

    @BeforeEach
    void setupPuzzleItemMapper() throws IOException {
        puzzleDAO = mock(PuzzleDAO.class);
        puzzleItemMapper = new PuzzleItemMapper(puzzleDAO);
    }

    @Test
    public void testToDTO() throws IOException {
        Puzzle puzzle = new Puzzle();
        when(puzzleDAO.get(puzzle.getId())).thenReturn(puzzle);
        PuzzleItem item = new PuzzleItem(puzzle,2);
        PuzzleItemDTO dto = new PuzzleItemDTO(puzzle,2);

        PuzzleItemDTO actual = puzzleItemMapper.toDTO(item);

        assertEquals(dto, actual);
    }

    @Test
    public void testToModel() throws IOException{
        Puzzle puzzle = new Puzzle();
        when(puzzleDAO.get(puzzle.getId())).thenReturn(puzzle);
        PuzzleItem item = new PuzzleItem(puzzle,2);
        PuzzleItemDTO dto = new PuzzleItemDTO(puzzle,2);

        PuzzleItem actual = puzzleItemMapper.toModel(dto);

        assertEquals(item, actual);

    }
    
}
