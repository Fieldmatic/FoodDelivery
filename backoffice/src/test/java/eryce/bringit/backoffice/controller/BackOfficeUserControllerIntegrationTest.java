package eryce.bringit.backoffice.controller;

import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackOfficeUserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUser_nonExistent_returnNotFound() {
        Long id = -1L;
        ResponseEntity<UserDto> getUserResponse = restTemplate.getForEntity("/back-office/user/{id}", UserDto.class, id);
        assertThat(getUserResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertNull(getUserResponse.getBody());
    }

    @ParameterizedTest
    @MethodSource("getEmptyStrings")
    public void createUser_missingEmail_returnBadRequest(String email) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .address("Svetozara Corovica 4")
                .name("Mico")
                .phone("0653343701")
                .email(email)
                .build();
        ResponseEntity<UserDto> createUserResponse = restTemplate.postForEntity("/back-office/user", createUserRequest, UserDto.class);
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    static List<String> getEmptyStrings() {
        List<String> strings = new ArrayList<>();
        strings.add(null);
        strings.add("");
        return strings;
    }

    @Test
    public void createUser_success() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("mmilic@capcade.com")
                .address("Svetozara Corovica 4")
                .name("Mico")
                .phone("0653343701")
                .build();

        ResponseEntity<UserDto> createUserResponse = restTemplate.postForEntity("/back-office/user", createUserRequest, UserDto.class);
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(createUserResponse.getBody());

        UserDto createUserResponseDTO = createUserResponse.getBody();
        assertThat(createUserResponseDTO.getEmail()).isEqualTo(createUserRequest.getEmail());
        assertThat(createUserResponseDTO.getAddress()).isEqualTo(createUserRequest.getAddress());
        assertThat(createUserResponseDTO.getName()).isEqualTo(createUserRequest.getName());
        assertThat(createUserResponseDTO.getPhone()).isEqualTo(createUserRequest.getPhone());
    }

    @Test
    public void getUser_success() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("mmilic@capcade.com")
                .address("Svetozara Corovica 4")
                .name("Mico")
                .phone("0653343701")
                .build();

        ResponseEntity<UserDto> createUserResponse = restTemplate.postForEntity("/back-office/user", createUserRequest, UserDto.class);
        assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(createUserResponse.getBody());

        UserDto createUserResponseDTO = createUserResponse.getBody();
        Long userId = createUserResponseDTO.getId();
        ResponseEntity<UserDto> getUserResponse = restTemplate.getForEntity("/back-office/user/{id}", UserDto.class, userId);
        assertThat(getUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(getUserResponse.getBody());

        UserDto getUserResponseDTO = getUserResponse.getBody();
        assertThat(getUserResponseDTO.getEmail()).isEqualTo(createUserRequest.getEmail());
        assertThat(getUserResponseDTO.getAddress()).isEqualTo(createUserRequest.getAddress());
        assertThat(getUserResponseDTO.getName()).isEqualTo(createUserRequest.getName());
        assertThat(getUserResponseDTO.getPhone()).isEqualTo(createUserRequest.getPhone());
    }
}
