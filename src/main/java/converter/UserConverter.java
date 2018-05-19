package converter;

import dto.UserDto;
import entity.User;

public interface UserConverter {

    UserDto convertUserToDto(User user);

}
