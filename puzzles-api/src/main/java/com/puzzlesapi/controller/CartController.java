package com.puzzlesapi.controller;

import com.puzzlesapi.dto.CartDTO;
import com.puzzlesapi.mapper.CartMapper;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.persistence.CartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("carts")
public class CartController {
    private final CartDAO cartDao;
    private final CartMapper cartMapper;
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());

    public CartController(CartDAO cartDao, CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }


    /**
     * Retrieves the cart for the given account
     * @param accountId The id of the account to get the cart for
     * @return The cart for the given account
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable int accountId) {
        LOG.info("GET /carts/" + accountId);
        Cart cart = cartDao.getByAccount(accountId);
        if (cart != null) {
            CartDTO cartDTO = cartMapper.toDTO(cart);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates the cart for the given account
     * @param accountId The id of the account to update the cart for
     * @param cartDTO The updated cart
     * @return The updated cart
     */
    @PutMapping("/{accountId}/update")
    public ResponseEntity<CartDTO> updateCart(@PathVariable int accountId, @RequestBody CartDTO cartDTO) {
        LOG.info("PUT /carts/" + accountId + "/update");
        try{
            Cart cart = cartMapper.toModel(cartDTO);
            Cart updatedCart = cartDao.update(cart);
            CartDTO updatedCartDTO = cartMapper.toDTO(updatedCart);
            return new ResponseEntity<>(updatedCartDTO, HttpStatus.OK);
        }
        catch (IllegalArgumentException | IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a puzzle to the cart for the given account if the account exists and the puzzle exists
     * @param accountId The id of the account to add the puzzle to
     * @param puzzleId The id of the puzzle to add
     * @return The updated cart if the puzzle was added, or a 404 if the puzzle was not found
     */
    @PostMapping("/{accountId}/add")
    public ResponseEntity<Cart> addPuzzle(@PathVariable int accountId, @RequestParam int puzzleId, @RequestParam Optional<Integer> quantity) {
        LOG.info("POST /carts/" + accountId + "/add/" + puzzleId);
        try{
            boolean result;
            if(quantity.isEmpty())
                result = cartDao.addPuzzle(accountId, puzzleId);
            else
                result = cartDao.addPuzzle(accountId, puzzleId, quantity.get());

            if(result)
                return new ResponseEntity<>(cartDao.getByAccount(accountId), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes a puzzle from the cart for the given account if the account exists and the puzzle exists
     * @param accountId The id of the account to remove the puzzle from
     * @param puzzleId The id of the puzzle to remove
     * @return The updated cart if the puzzle was removed, or a 404 if the puzzle was not found
     */
    @DeleteMapping("/{accountId}/remove")
    public ResponseEntity<Cart> removePuzzle(@PathVariable int accountId, @RequestParam int puzzleId) throws IOException {
        LOG.info("DELETE /carts/" + accountId + "/remove/" + puzzleId);
        try{
            if(cartDao.removePuzzle(accountId, puzzleId))
                return new ResponseEntity<>(cartDao.getByAccount(accountId), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
