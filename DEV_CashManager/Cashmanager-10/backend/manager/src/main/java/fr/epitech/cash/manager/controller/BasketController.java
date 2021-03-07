package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.dto.BasketDto;
import fr.epitech.cash.manager.entity.BasketEntity;
import fr.epitech.cash.manager.repository.BasketRepository;
import fr.epitech.cash.manager.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketRepository basketRepository;

    @PostMapping(value = "/api/auth/basket")
    public ResponseEntity<BasketDto> createBasket(
            @RequestParam String name, @RequestParam Integer quantity,
            @RequestParam Double amount, @RequestParam Boolean state) {

        BasketDto basketDto = basketService.createBasket(
                name, quantity, amount, state
        );
        return new ResponseEntity<>(basketDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/api/auth/baskets/{id}")
    public ResponseEntity<HttpStatus> deleteBasket(@PathVariable Integer id) {

        basketService.deleteBasket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/api/auth/baskets")
    public ResponseEntity<List<BasketDto>> getBaskets() {
        List<BasketDto> basketDtoList = basketService.getBaskets();

        return new ResponseEntity<>(basketDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/api/auth/baskets/{id}")
    public ResponseEntity<BasketDto> getBasket(@PathVariable Integer id) {
        BasketDto basketDto = basketService.getBasket(id);
        return new ResponseEntity<>(basketDto, HttpStatus.OK);
    }

    @PatchMapping("/api/auth/baskets/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") Integer id,
                                             @RequestBody BasketDto BasketDto) {
        basketService.updateBasket(id, BasketDto);

        return new ResponseEntity<>(HttpStatus.OK);
    };
}