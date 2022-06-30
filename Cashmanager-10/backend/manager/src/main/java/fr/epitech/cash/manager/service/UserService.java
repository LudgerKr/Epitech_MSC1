package fr.epitech.cash.manager.service;

import fr.epitech.cash.manager.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Create a new user
     * @param firstName : firstName
     * @param lastName : lastName
     * @param email : email
     * @param password : password
     * @param street : street
     * @param city : city
     * @param zipCode : zipCode
     * @param phoneNumber : phoneNumber
     * @param role_id : role_id
     * @return : UserDto
     */
    UserDto createUser(String firstName, String lastName, String email,
                       String password, String street, String city,
                       String zipCode, String phoneNumber, Integer role_id);

    /**
     * Delete an user by its id
     * @param id : id
     */
    void deleteUser(Integer id);

    /**
     * Return a list of all users
     * @return : List<UserDto>
     */
    List<UserDto> getUsers();

    /**
     * Return on user by id
     * @return UserDto
     * @param id : id
     */
    UserDto getUser(Integer id);

    /**
     * Return on user by email
     * @return UserDto
     * @param email : email
     */
    UserDto getUserbyEmail(String email);

    /**
     * Update an user
     * @param id : id
     * @param userDto : userDto
     */
    void updateUser(Integer id, UserDto userDto);
}
