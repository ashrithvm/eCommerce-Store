package com.puzzlesapi.controller;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.mapper.CustomPuzzleMapper;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.persistence.CustomPuzzleDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("customPuzzles")
public class CustomPuzzleController {
    private final Logger LOG = Logger.getLogger(CustomPuzzleController.class.getName());
    private final CustomPuzzleMapper customPuzzleMapper;
    private final CustomPuzzleDAO customPuzzleDAO;
    public CustomPuzzleController(CustomPuzzleMapper customPuzzleMapper, CustomPuzzleDAO customPuzzleDAO) {
        this.customPuzzleMapper = customPuzzleMapper;
        this.customPuzzleDAO = customPuzzleDAO;
    }

    /**
     * Retrieves all custom puzzles created by an account.
     * @param accountId The id of the account to get the custom puzzles for
     * @return A list of custom puzzles created by the account or a 500 status code if an error occurs
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<List<CustomPuzzleDTO>> getCustomPuzzlesByAccount(@PathVariable int accountId) {
        try {
            List<CustomPuzzle> customPuzzles = customPuzzleDAO.getByAccount(accountId);
            List<CustomPuzzleDTO> customPuzzleDTOs = customPuzzles.stream().map(customPuzzle -> {
                try {
                    return customPuzzleMapper.toDTO(customPuzzle);
                } catch (IOException e) {
                    LOG.warning("ERROR: " + e.getMessage());
                    return null;
                }
            }).toList();
            return new ResponseEntity<>(customPuzzleDTOs, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new custom puzzle.
     * @param customPuzzleDTO The custom puzzle to create
     * @return The created custom puzzle or a 500 status code if an error occurs
     */
    @PostMapping("")
    public ResponseEntity<CustomPuzzleDTO> createCustomPuzzle(@RequestBody CustomPuzzleDTO customPuzzleDTO) {
        CustomPuzzle customPuzzle = customPuzzleMapper.toModel(customPuzzleDTO);
        try {
            CustomPuzzle createdPuzzle = customPuzzleDAO.create(customPuzzle);
            return new ResponseEntity<>(customPuzzleMapper.toDTO(createdPuzzle), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a custom puzzle.
     * @param id The id of the custom puzzle to delete
     * @return A 200 status code if the custom puzzle was deleted, a 404 status code if the custom puzzle does not exist, or a 500 status code if an error occurs
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomPuzzleDTO> deleteCustomPuzzle(@PathVariable int id) {
        try {
            if(customPuzzleDAO.delete(id))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}