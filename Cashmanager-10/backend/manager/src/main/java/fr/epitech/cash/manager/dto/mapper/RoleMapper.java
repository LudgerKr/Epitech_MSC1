package fr.epitech.cash.manager.dto.mapper;

import fr.epitech.cash.manager.dto.RoleDto;
import fr.epitech.cash.manager.entity.RoleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Convert easily an Entity into DTO
    @Named(value = "convertInvoiceEntityToDto")
    RoleDto convert(RoleEntity roleEntity);

    // Convert easily a list of Entity into DTO
    @IterableMapping(qualifiedByName = "convertInvoiceEntityToDto", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<RoleDto> convertListRoleEntity(List<RoleEntity> invoiceEntityList);

    // Convert easily an Entity into DTO for updating
    @Mapping(target = "id", ignore = true)
    void updateRoleEntity(RoleDto roleDto, @MappingTarget RoleEntity roleEntity);
}