package eryce.bringit.api.controller;

import eryce.bringit.api.service.BackOfficeClientProxy;
import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/back-office/")
@RequiredArgsConstructor
public class BackOfficeUserController {

    private final BackOfficeClientProxy backOfficeClientProxy;

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest user) {
        Optional<UserDto> newUser = backOfficeClientProxy.createUser(user);
        if (newUser.isPresent()) {
            return ResponseEntity.ok(newUser.get());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        Optional<UserDto> user = backOfficeClientProxy.getUser(userId);

        return ResponseEntity.of(user);
    }
}
