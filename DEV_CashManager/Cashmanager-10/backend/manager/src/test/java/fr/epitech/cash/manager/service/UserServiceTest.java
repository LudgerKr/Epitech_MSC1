package fr.epitech.cash.manager.service;


import fr.epitech.cash.manager.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser()
    {
        // Given
        String firstName = "Richard";
        String lastName = "La";
        String email = "rdla@hotmail.fr";
        String password = "toto";
        String street = "4 rue des soeurs";
        String city = "Strasbourg";
        String zipCode = "65000";
        String phoneNumber = "0123456789";
        Integer role_id = 59;

        // When
        UserDto userDto = userService.createUser(
                firstName, lastName, email, password,
                street, city, zipCode, phoneNumber, role_id
        );
        // Then
        Assertions.assertEquals(userDto.getFirstName(), firstName);
        Assertions.assertEquals(userDto.getLastName(), lastName);
        Assertions.assertEquals(userDto.getEmail(), email);
        Assertions.assertNotNull(userDto.getId());
    }

}
