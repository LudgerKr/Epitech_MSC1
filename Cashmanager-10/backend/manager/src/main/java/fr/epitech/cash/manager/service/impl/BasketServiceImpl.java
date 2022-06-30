package fr.epitech.cash.manager.service.impl;

import fr.epitech.cash.manager.dto.BasketDto;
import fr.epitech.cash.manager.dto.mapper.BasketMapper;
import fr.epitech.cash.manager.entity.BasketEntity;
import fr.epitech.cash.manager.repository.BasketRepository;
import fr.epitech.cash.manager.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service("basketDetailsService")
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;


    @Autowired
    private BasketMapper basketMapper;

    @Override
    @Transactional
    public BasketDto createBasket(String name, Integer quantity, Double amount,
                              Boolean state)
    {
        BasketEntity basketEntity = BasketEntity.builder()
                                        .name(name)
                                        .quantity(quantity)
                                        .amount(amount)
                                        .state(state)
                                        .creation_date(new Timestamp(System.currentTimeMillis()))
                                        .modification_date(new Timestamp(System.currentTimeMillis()))
                                        .build();

        basketEntity = basketRepository.save(basketEntity);

        return BasketDto.builder()
                .id(basketEntity.getId())
                .name(basketEntity.getName())
                .quantity(basketEntity.getQuantity())
                .amount(basketEntity.getAmount())
                .state(basketEntity.getState())
                .creation_date(basketEntity.getCreation_date())
                .modification_date(basketEntity.getModification_date())
                .build();
    }

    @Override
    @Transactional
    public void deleteBasket(Integer id) {
        basketRepository.deleteById(id);
    }

    @Override
    public List<BasketDto> getBaskets()
    {
        List<BasketEntity> basketEntityList = basketRepository.findAll();
        return basketMapper.convertListBasketEntity(basketEntityList);
    }

    @Override
    public BasketDto getBasket(Integer id)
    {
        BasketEntity basketEntity = basketRepository.findById(id).orElse(null);
        if(basketEntity == null)
            return null;
        return basketMapper.convert(basketEntity);
    }

    @Override
    @Transactional
    public void updateBasket(Integer id, BasketDto basketDto)
    {
        Optional<BasketEntity> basketEntityOptional = basketRepository.findById(id);
        if (!basketEntityOptional.isPresent()) {
            ResponseEntity.notFound().build();
            return;
        }

        BasketEntity basketEntity = basketEntityOptional.get();

        if (basketDto.getName() != null) {
            basketEntity.setName(basketDto.getName());
        }
        if (basketDto.getQuantity() != null) {
            basketEntity.setQuantity(basketDto.getQuantity());
        }
        if (basketDto.getAmount() != null) {
            basketEntity.setAmount(basketDto.getAmount());
        }
        if (basketDto.getState() != null) {
            basketEntity.setState(basketDto.getState());
        }
        
        basketEntity.setModification_date(new Timestamp(System.currentTimeMillis()));
        basketRepository.save(basketEntity);
    }
}