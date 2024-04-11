package com.puzzlesapi.mapper;

import com.puzzlesapi.dto.CartDTO;
import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.dto.PuzzleItemDTO;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.PuzzleItem;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Maps a Cart object to a CartDTO object and vice versa.
 */
@Component
public class CartMapper {
    private final PuzzleItemMapper puzzleItemMapper;
    private final CustomPuzzleMapper customPuzzleMapper;

    public CartMapper(PuzzleItemMapper puzzleItemMapper, CustomPuzzleMapper customPuzzleMapper) {
        this.puzzleItemMapper = puzzleItemMapper;
        this.customPuzzleMapper = customPuzzleMapper;
    }

    /**
     * Maps a Cart object to a CartDTO object.
     * @param cart The Cart object to be mapped.
     * @return The CartDTO object.
     */
    public CartDTO toDTO(Cart cart) {
        List<PuzzleItemDTO> puzzleItemDTOs = cart.getPuzzles().stream().map(
                puzzleCartItem -> {
                    try {
                        return puzzleItemMapper.toDTO(puzzleCartItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toList();

        List<CustomPuzzleDTO> customPuzzleDTOs = cart.getCustomPuzzles().stream().map(
                customPuzzle -> {
                    try {
                        return customPuzzleMapper.toDTO(customPuzzle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toList();

        return new CartDTO(cart.getId(), puzzleItemDTOs, customPuzzleDTOs);
    }

    /**
     * Maps a CartDTO object to a Cart object.
     * @param cartDTO The CartDTO object to be mapped.
     * @return The Cart object.
     */
    public Cart toModel(CartDTO cartDTO) {
        LinkedList<PuzzleItem> puzzleItems = cartDTO.getItems().stream().map(
                puzzleItemDTO -> {
                    try {
                        return puzzleItemMapper.toModel(puzzleItemDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toCollection(LinkedList::new));

        List<CustomPuzzle> customPuzzles = cartDTO.getCustomPuzzles().stream().map(
                customPuzzleDTO -> {
                    try {
                        return customPuzzleMapper.toModel(customPuzzleDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toCollection(LinkedList::new));
        return new Cart(cartDTO.getId(), puzzleItems, customPuzzles);
    }
}
