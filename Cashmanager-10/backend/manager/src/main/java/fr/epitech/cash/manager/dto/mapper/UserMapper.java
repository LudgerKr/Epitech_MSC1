package fr.epitech.cash.manager.dto.mapper;

import fr.epitech.cash.manager.dto.UserDto;
import fr.epitech.cash.manager.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Convert easily an Entity into DTO
    @Named(value = "convertUserEntityToDto")
    UserDto convert(UserEntity userEntity);

    // Convert easily a list of Entity into DTO
    @IterableMapping(qualifiedByName = "convertUserEntityToDto", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<UserDto> convertListUserEntity(List<UserEntity> userEntityList);

    // Convert easily an Entity into DTO for updating
    @Mapping(target = "id", ignore = true)
    void updateUserEntity(UserDto userDto, @MappingTarget UserEntity userEntity);
}