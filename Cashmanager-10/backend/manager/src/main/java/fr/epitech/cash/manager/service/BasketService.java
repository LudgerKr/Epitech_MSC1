package fr.epitech.cash.manager.service;

import fr.epitech.cash.manager.dto.BasketDto;

import java.util.List;

public interface BasketService {

    /**
     * Create a new basket
     * @param name : name
     * @param quantity : quantity
     * @param amount : amount
     * @param state : state
     * @return : BasketDto
     */
    BasketDto createBasket(String name, Integer quantity, Double amount,
                       Boolean state);

    /**
     * Delete an basket by its id
     * @param id : id
     */
    void deleteBasket(Integer id);

    /**
     * Return a list of all baskets
     * @return : List<BasketDto>
     */
    List<BasketDto> getBaskets();

    /**
     * Return on basket by id
     * @return BasketDto
     * @param id : id
     */
    BasketDto getBasket(Integer id);

    /**
     * Update an basket
     * @param id : id
     * @param basketDto : basketDto
     */
    void updateBasket(Integer id, BasketDto basketDto);

}