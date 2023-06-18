package eryce.bringit.backoffice.service;

import eryce.bringit.backoffice.entity.User;
import eryce.bringit.backoffice.repository.UserRepository;
import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Optional<UserDto> getUser(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.map(user -> modelMapper.map(user, UserDto.class))
                .or(Optional::empty);
    }

    public UserDto createUser(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);

        User newUser = userRepository.save(user);

        return modelMapper.map(newUser, UserDto.class);
    }
}
