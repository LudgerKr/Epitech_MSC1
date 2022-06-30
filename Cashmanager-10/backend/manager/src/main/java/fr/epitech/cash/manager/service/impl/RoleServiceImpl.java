package fr.epitech.cash.manager.service.impl;

import fr.epitech.cash.manager.dto.RoleDto;
import fr.epitech.cash.manager.dto.mapper.RoleMapper;
import fr.epitech.cash.manager.entity.RoleEntity;
import fr.epitech.cash.manager.repository.RoleRepository;
import fr.epitech.cash.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service("roleDetailsService")
public class RoleServiceImpl implements RoleService {

    // Prepare the RoleRepository
    @Autowired
    private RoleRepository roleRepository;

    // Prepare the RoleMapper
    @Autowired
    private RoleMapper roleMapper;

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Create role
    public RoleDto createRole(String name)
    {
        RoleEntity roleEntity = RoleEntity.builder()
                                        .name(name)
                                        // Set Datetime
                                        .creation_date(new Timestamp(System.currentTimeMillis()))
                                        .modification_date(new Timestamp(System.currentTimeMillis()))
                                        .build();

        // Record the role in the database
        roleEntity = roleRepository.save(roleEntity);

        return RoleDto.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .creation_date(roleEntity.getCreation_date())
                .modification_date(roleEntity.getModification_date())
                .build();
    }

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Delete a role with its id
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    // Return all roles
    public List<RoleDto> getRoles()
    {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleMapper.convertListRoleEntity(roleEntityList);
    }

    @Override
    // Return a role with its id
    public RoleDto getRole(Integer id)
    {
        RoleEntity roleEntity = roleRepository.findById(id).orElse(null);
        if(roleEntity == null)
            return null;
        return roleMapper.convert(roleEntity);
    }

    @Override
    // Transactional indicates it will modify the database
    @Transactional
    // Update a role with its id
    public void updateRole(Integer id, RoleDto roleDto)
    {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
        if (!roleEntityOptional.isPresent()) {
            ResponseEntity.notFound().build();
            return;
        }

        RoleEntity roleEntity = roleEntityOptional.get();

        if (roleDto.getName() != null) {
            roleEntity.setName(roleDto.getName());
        }

        // Set Datetime to the modification date
        roleEntity.setModification_date(new Timestamp(System.currentTimeMillis()));

        // Record the modify role in the database
        roleRepository.save(roleEntity);
    }
}