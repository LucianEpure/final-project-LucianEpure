package service;


import dto.UserDto;
import entity.Role;
import entity.User;
import entity.builder.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import repository.UserRepository;
import validators.IValidator;
import validators.Notification;
import validators.UserValidator;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private IValidator validator;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public Notification<Boolean> register(UserDto user, String type) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        validator = new UserValidator(user);
        boolean userValid = validator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<Boolean>();
        if(!userValid){
            validator.getErrors().forEach(userRegisterNotification::addError);
            System.out.println(userRegisterNotification.getFormattedErrors());
            userRegisterNotification.setResult(Boolean.FALSE);
        }
        else{
            User dbUser = new UserBuilder().setUsername(user.getUsername()).setPassword(enc.encode(user.getPassword())).build();
            List<Role> userRoles = dbUser.getRoles();
            userRoles.add(roleRepository.findByRoleName(type));
            dbUser.setRoles(userRoles);
            System.out.println(dbUser.getRoles());
            userRepository.save(dbUser);
            userRegisterNotification.setResult(Boolean.TRUE);
        }
        return userRegisterNotification;
    }
}
