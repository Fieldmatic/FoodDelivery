package eryce.bringit.backoffice.controller;

import eryce.bringit.backoffice.service.UserService;
import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/back-office")
@RequiredArgsConstructor
public class BackOfficeUserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUser(id);

        return ResponseEntity.of(user);
    }
}
