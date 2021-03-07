package fr.epitech.cash.manager.service.impl;

import fr.epitech.cash.manager.dto.UserDto;
import fr.epitech.cash.manager.dto.mapper.UserMapper;
import fr.epitech.cash.manager.entity.UserEntity;
import fr.epitech.cash.manager.repository.RoleRepository;
import fr.epitech.cash.manager.repository.UserRepository;
import fr.epitech.cash.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String email)
    {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null)
            throw new UsernameNotFoundException("L'utilisateur n'existe pas" + email);

        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    }


    @Override
    @Transactional
    public UserDto createUser(String firstName, String lastName, String email,
                              String password, String street, String city,
                              String zipCode, String phoneNumber, Integer role_id)
    {
        PasswordEncoder hashPassword = this.passwordEncoder();
        UserEntity userEntity = UserEntity.builder()
                                        .firstName(firstName)
                                        .lastName(lastName)
                                        .email(email)
                                        .password(hashPassword.encode(password))
                                        .street(street)
                                        .city(city)
                                        .zipCode(zipCode)
                                        .phoneNumber(phoneNumber)
                                        .roleEntity(roleRepository.getOne(role_id))
                                        .creation_date(new Timestamp(System.currentTimeMillis()))
                                        .modification_date(new Timestamp(System.currentTimeMillis()))
                                        .build();

        userEntity = userRepository.save(userEntity);

        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .street(userEntity.getStreet())
                .city(userEntity.getCity())
                .zipCode(userEntity.getZipCode())
                .phoneNumber(userEntity.getPhoneNumber())
                .role(userEntity.getRoleEntity().getName())
                .creation_date(userEntity.getCreation_date())
                .modification_date(userEntity.getModification_date())
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers()
    {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userMapper.convertListUserEntity(userEntityList);
    }

    @Override
    public UserDto getUser(Integer id)
    {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null)
            return null;
        return userMapper.convert(userEntity);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserDto userDto)
    {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (!userEntityOptional.isPresent()) {
            ResponseEntity.notFound().build();
            return;
        }

        UserEntity userEntity = userEntityOptional.get();

        if (userDto.getFirstName() != null) {
            userEntity.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            userEntity.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null) {
            userEntity.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            PasswordEncoder hashPassword = this.passwordEncoder();
            userEntity.setPassword(hashPassword.encode(userDto.getPassword()));
        }
        if (userDto.getStreet() != null) {
            userEntity.setStreet(userDto.getStreet());
        }
        if (userDto.getCity() != null) {
            userEntity.setCity(userDto.getCity());
        }
        if (userDto.getZipCode() != null) {
            userEntity.setZipCode(userDto.getZipCode());
        }
        if (userDto.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
        }

        userDto.setModification_date(new Timestamp(System.currentTimeMillis()));

        userRepository.save(userEntity);
    }
}
