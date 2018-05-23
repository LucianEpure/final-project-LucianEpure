package service.regiment;

import dto.UserDto;

public interface UserService {

    void registerUser(UserDto userDto,String type);

    UserDto findAdmin();

    UserDto findUser(String username);
}
