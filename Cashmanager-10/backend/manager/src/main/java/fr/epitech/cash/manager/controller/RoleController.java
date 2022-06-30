package fr.epitech.cash.manager.controller;

import fr.epitech.cash.manager.dto.RoleDto;
import fr.epitech.cash.manager.entity.RoleEntity;
import fr.epitech.cash.manager.repository.RoleRepository;
import fr.epitech.cash.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class RoleController {

    // Prepare the RoleService
    @Autowired
    private RoleService roleService;

    // Prepare the RoleRepository
    @Autowired
    private RoleRepository roleRepository;

    // Create a role
    @PostMapping(value = "/api/auth/role")
    public ResponseEntity<RoleDto> createRole(
            @RequestParam String name) {

        RoleDto roleDto = roleService.createRole(
                name
        );
        return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
    }

    // Delete a role with its id
    @DeleteMapping(value = "/api/auth/roles/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Integer id) {

        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all roles
    @GetMapping(value = "/api/auth/roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<RoleDto> roleDtoList = roleService.getRoles();

        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    // Get a role with its id
    @GetMapping(value = "/api/auth/roles/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Integer id) {
        RoleDto roleDto = roleService.getRole(id);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    // Update a role with its id
    @PatchMapping("/api/auth/roles/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@PathVariable("id") Integer id,
                                             @RequestBody RoleDto RoleDto) {
        roleService.updateRole(id, RoleDto);

        return new ResponseEntity<>(HttpStatus.OK);
    };
}