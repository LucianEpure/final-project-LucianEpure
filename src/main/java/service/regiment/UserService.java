package service.regiment;

import dto.UserDto;

public interface UserService {

    void registerAdmin(UserDto userDto);

    UserDto findAdmin();

    UserDto findUser(String username);
}
