package converter;

import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
import dto.UserDto;
import entity.Regiment;
import entity.Requirement;
import entity.Supply;
import entity.User;

public interface RegimentConverter {
    UserDto convertUserToDto(User user);

    RegimentDto convertRegimentToDto(Regiment regiment);


}
