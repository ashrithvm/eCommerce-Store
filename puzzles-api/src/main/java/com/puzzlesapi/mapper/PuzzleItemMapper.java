package com.puzzlesapi.mapper;

import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.model.PuzzleItem;
import com.puzzlesapi.persistence.PuzzleDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Maps a PuzzleItem object to a PuzzleItemDTO object and vice versa.
 */
@Component
public class PuzzleItemMapper {
    private PuzzleDAO puzzleDAO;

    public PuzzleItemMapper( PuzzleDAO puzzleDAO){
        this.puzzleDAO = puzzleDAO;
    }

    /**
     * Maps a PuzzleItem object to a PuzzleItemDTO object.
     * @param puzzleItem The PuzzleItem object to be mapped.
     * @return The PuzzleItemDTO object.
     */
    public PuzzleItemDTO toDTO(PuzzleItem puzzleItem) throws IOException {
        Puzzle puzzle = puzzleDAO.get(puzzleItem.getId());
        return new PuzzleItemDTO(puzzle, puzzleItem.getQuantity());
    }

    /**
     * Maps a PuzzleItemDTO object to a PuzzleItem object.
     * @param puzzleItemDTO The PuzzleItemDTO object to be mapped.
     * @return The PuzzleItem object.
     */
    public PuzzleItem toModel(PuzzleItemDTO puzzleItemDTO) {
        return new PuzzleItem(puzzleItemDTO.getPuzzle(), puzzleItemDTO.getQuantity());
    }
}
