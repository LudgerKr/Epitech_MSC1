package fr.epitech.cash.manager.service;

import fr.epitech.cash.manager.dto.RoleDto;

import java.util.List;

public interface RoleService {

    /**
     * Create a new role
     * @param name : name
     * @return : RoleDto
     */
    RoleDto createRole(String name);

    /**
     * Delete an role by its id
     * @param id : id
     */
    void deleteRole(Integer id);

    /**
     * Return a list of all roles
     * @return : List<RoleDto>
     */
    List<RoleDto> getRoles();

    /**
     * Return on role by id
     * @return RoleDto
     * @param id : id
     */
    RoleDto getRole(Integer id);

    /**
     * Update an role
     * @param id : id
     * @param roleDto : roleDto
     */
    void updateRole(Integer id, RoleDto roleDto);

}