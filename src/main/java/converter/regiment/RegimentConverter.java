package converter.regiment;

import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
import dto.UserDto;
import entity.Regiment;
import entity.Requirement;
import entity.Supply;
import entity.User;

public interface RegimentConverter {

    RegimentDto convertRegimentToDto(Regiment regiment);


}
