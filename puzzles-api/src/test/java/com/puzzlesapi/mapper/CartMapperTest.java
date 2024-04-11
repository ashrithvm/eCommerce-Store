package com.puzzlesapi.mapper;

import com.puzzlesapi.model.Cart;
import com.puzzlesapi.model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import com.puzzlesapi.dto.CartDTO;
import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.model.PuzzleItem;
import com.puzzlesapi.persistence.PuzzleDAO;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("mapper-tier")

public class CartMapperTest {
    private CartMapper cartMapper;
    private CustomPuzzleMapper customMapper;
    private PuzzleItemMapper puzzleMapper;
    

    @BeforeEach
    void setupPuzzleItemMapper() throws IOException {
        customMapper = mock(CustomPuzzleMapper.class);
        puzzleMapper = mock(PuzzleItemMapper.class);
        cartMapper = new CartMapper(puzzleMapper, customMapper);
    }

    @Test
    void testToDTO() throws IOException{
        LinkedList<PuzzleItem> list = new LinkedList();
        Puzzle puzzle = new Puzzle();
        PuzzleItem item = new PuzzleItem(puzzle, 1);
        PuzzleItemDTO expectDTO = new PuzzleItemDTO(puzzle, 1);
        LinkedList<PuzzleItemDTO> dtoList = new LinkedList<>();
        dtoList.add(expectDTO);
        LinkedList<CustomPuzzleDTO> customList = new LinkedList<>();

        list.add(item);
        Cart cart = new Cart(1,list);
        CartDTO expected = new CartDTO(1, dtoList, customList);
        when(puzzleMapper.toDTO(item)).thenReturn(expectDTO);
        CartDTO actual = cartMapper.toDTO(cart);

        assertEquals(expected.getId(), actual.getId());

    }
/* 
    @Test
    void testToDTOFail() throws IOException{
        LinkedList<PuzzleItem> list = new LinkedList();
        Puzzle puzzle = new Puzzle();
        PuzzleItem item = new PuzzleItem(puzzle, 1);
        PuzzleItemDTO expectDTO = new PuzzleItemDTO(puzzle, 1);
        LinkedList<PuzzleItemDTO> dtoList = new LinkedList<>();
        dtoList.add(expectDTO);
        LinkedList<CustomPuzzleDTO> customList = new LinkedList<>();

        list.add(item);
        Cart cart = new Cart(1,list);
        CartDTO expected = new CartDTO(1, dtoList, customList);
        when(puzzleMapper.toDTO(item)).thenThrow(new Exception("e"));
        CartDTO actual = cartMapper.toDTO(cart);

        assertNull(actual);


    }
    */

    @Test
    void testToModel(){
        LinkedList<PuzzleItem> list = new LinkedList();
        Puzzle puzzle = new Puzzle();
        PuzzleItem item = new PuzzleItem(puzzle, 1);
        PuzzleItemDTO expectDTO = new PuzzleItemDTO(puzzle, 1);
        LinkedList<PuzzleItemDTO> dtoList = new LinkedList<>();
        dtoList.add(expectDTO);
        LinkedList<CustomPuzzleDTO> customList = new LinkedList<>();

        list.add(item);
        Cart cart = new Cart(1,list);
        CartDTO expected = new CartDTO(1, dtoList, customList);
        when(puzzleMapper.toModel(expectDTO)).thenReturn(item);
        Cart actual = cartMapper.toModel(expected);

        assertEquals(cart.getId(), actual.getId());

    }
    
}
