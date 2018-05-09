package converter;

import dto.RegimentDto;
import dto.UserDto;
import entity.Regiment;
import entity.User;

public interface RegimentConverter {
    UserDto convertUserToDto(User user);

    RegimentDto convertRegimentToDto(Regiment regiment);
}
