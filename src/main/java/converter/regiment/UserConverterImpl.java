package converter.regiment;

import dto.UserDto;
import entity.Role;
import entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverterImpl implements UserConverter{

    @Override
    public UserDto convertUserToDto(User user){
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        List<Role> roles = user.getRoles();
        for(Role role:roles)
            userDto.setRoles(userDto.getRoles()+" "+role.getRoleName());
        return userDto;
    }
}
