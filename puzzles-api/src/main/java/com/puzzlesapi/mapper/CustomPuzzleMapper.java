package com.puzzlesapi.mapper;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.persistence.AccountDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Maps a CustomPuzzle object to a CustomPuzzleDTO object and vice versa.
 */
@Component
public class CustomPuzzleMapper {
    private AccountDAO accountDAO;

    public CustomPuzzleMapper(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    /**
     * Maps a CustomPuzzle object to a CustomPuzzleDTO object.
     * @param customPuzzle The CustomPuzzle object to be mapped.
     * @return The CustomPuzzleDTO object.
     */
    public CustomPuzzleDTO toDTO(CustomPuzzle customPuzzle) throws IOException {
        Account account = accountDAO.get(customPuzzle.getAccountID());
        return new CustomPuzzleDTO(customPuzzle.getId(), account, customPuzzle.getName(), customPuzzle.getQuantity(), customPuzzle.getDifficulty(), customPuzzle.getPrice(), customPuzzle.getImageURL());
    }

    /**
     * Maps a CustomPuzzleDTO object to a CustomPuzzle object.
     * @param customPuzzleDTO The CustomPuzzleDTO object to be mapped.
     * @return The CustomPuzzle object.
     */
    public CustomPuzzle toModel(CustomPuzzleDTO customPuzzleDTO) {
        return new CustomPuzzle(customPuzzleDTO.getId(), customPuzzleDTO.getAccount().getId(), customPuzzleDTO.getName(), customPuzzleDTO.getQuantity(), customPuzzleDTO.getDifficulty(), customPuzzleDTO.getImageURL());
    }
}
