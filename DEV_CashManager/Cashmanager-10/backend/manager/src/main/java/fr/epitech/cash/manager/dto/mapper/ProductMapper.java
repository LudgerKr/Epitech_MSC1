package fr.epitech.cash.manager.dto.mapper;

import fr.epitech.cash.manager.dto.ProductDto;
import fr.epitech.cash.manager.entity.ProductEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named(value = "convertInvoiceEntityToDto")
    ProductDto convert(ProductEntity productEntity);

    @IterableMapping(qualifiedByName = "convertInvoiceEntityToDto", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<ProductDto> convertListProductEntity(List<ProductEntity> invoiceEntityList);

    @Mapping(target = "id", ignore = true)
    void updateProductEntity(ProductDto productDto, @MappingTarget ProductEntity productEntity);
}