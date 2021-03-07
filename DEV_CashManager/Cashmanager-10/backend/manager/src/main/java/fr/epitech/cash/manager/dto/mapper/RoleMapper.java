package fr.epitech.cash.manager.dto.mapper;

import fr.epitech.cash.manager.dto.RoleDto;
import fr.epitech.cash.manager.entity.RoleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Named(value = "convertInvoiceEntityToDto")
    RoleDto convert(RoleEntity roleEntity);

    @IterableMapping(qualifiedByName = "convertInvoiceEntityToDto", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<RoleDto> convertListRoleEntity(List<RoleEntity> invoiceEntityList);

    @Mapping(target = "id", ignore = true)
    void updateRoleEntity(RoleDto roleDto, @MappingTarget RoleEntity roleEntity);
}