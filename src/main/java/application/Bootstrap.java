package application;

import dto.UserDto;
import entity.Role;
import entity.Type;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import repository.RoleRepository;
import repository.TypeRepository;
import repository.UserRepository;
import service.RegimentService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Component
public class Bootstrap {
    private RoleRepository roleRepository;
    private TypeRepository typeRepository;
    private RegimentService regimentService;
    private RegimentService userService;
    private UserRepository userRepository;


    @Autowired
    public Bootstrap(RoleRepository roleRepository, RegimentService userService, TypeRepository typeRepository, RegimentService regimentService, UserRepository userRepository ){
        this.roleRepository = roleRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.regimentService = regimentService;
        this.userService = userService;

    }

    @PostConstruct
    private void initialize(){
        initRoles();
        initTypes();
        initUsers();

    }

    private void initRoles() {
        Role quartermaster = new Role(Constants.QUARTERMASTER);
        Role regimentCommander = new Role(Constants.REGIMENTCOMMANDER);
        roleRepository.save(quartermaster);
        roleRepository.save(regimentCommander);
    }

    private void initTypes(){
        Type recruits  = new Type(Constants.RECRUITS);
        Type infantry = new Type(Constants.INFANTRY);
        Type assault = new Type(Constants.ASSAULT);
        Type medics = new Type(Constants.MEDICS);
        Type engineers = new Type(Constants.ENGINEERS);
        typeRepository.save(infantry);
        typeRepository.save(assault);
        typeRepository.save(medics);
        typeRepository.save(engineers);
        typeRepository.save(recruits);
    }

    private void initUsers(){
        UserDto user1 = new UserDto();
        user1.setUsername("QM1@yahoo.com");
        user1.setPassword("aB123456!");
        userService.registerAdmin(user1);
        regimentService.enlistRegiment(123456,"aB123456!");

    }

}


