package fr.epitech.cash.manager.dto.mapper;

import fr.epitech.cash.manager.dto.BasketDto;
import fr.epitech.cash.manager.entity.BasketEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketMapper {

    @Named(value = "convertInvoiceEntityToDto")
    BasketDto convert(BasketEntity basketEntity);

    @IterableMapping(qualifiedByName = "convertInvoiceEntityToDto", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<BasketDto> convertListBasketEntity(List<BasketEntity> invoiceEntityList);

    @Mapping(target = "id", ignore = true)
    void updateBasketEntity(BasketDto basketDto, @MappingTarget BasketEntity basketEntity);
}