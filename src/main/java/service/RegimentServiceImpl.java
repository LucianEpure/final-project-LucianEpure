package service;


import application.Constants;
import dto.UserDto;
import entity.*;
import entity.builder.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.*;
import validators.IValidator;
import validators.Notification;
import validators.RegimentValidator;
import validators.UserValidator;

import java.util.List;

@Service
public class RegimentServiceImpl implements RegimentService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RegimentRepository regimentRepository;
    private TypeRepository typeRepository;
    private RequirementRepository requirementRepository;
    private SupplyRepository supplyRepository;
    private IValidator validator;


    @Autowired
    public RegimentServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RegimentRepository regimentRepository, TypeRepository typeRepository, SupplyRepository supplyRepository, RequirementRepository requirementRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.regimentRepository = regimentRepository;
        this.typeRepository = typeRepository;
        this.supplyRepository = supplyRepository;
        this.requirementRepository = requirementRepository;
    }


    @Override
    public void registerAdmin(UserDto user) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        User dbUser = new User();
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(enc.encode(user.getPassword()));
        List<Role> userRoles = dbUser.getRoles();
        userRoles.add(roleRepository.findByRoleName(Constants.QUARTERMASTER));
        dbUser.setRoles(userRoles);
        userRepository.save(dbUser);
    }

    @Override
    public Notification<Boolean> enlistRegiment(int code, String password) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        validator = new RegimentValidator(code,password);
        boolean regimentValid = validator.validate();
        Notification<Boolean> regimentRegisterNotification = new Notification<>();
        if(!regimentValid){ validator.getErrors().forEach(regimentRegisterNotification::addError);
            System.out.println(regimentRegisterNotification.getFormattedErrors());
            regimentRegisterNotification.setResult(Boolean.FALSE);
        }
        else{
            Regiment regiment = new Regiment();
            Requirement requirement= new Requirement();
            User user = new User();
            Supply supply = new Supply();
            requirementRepository.save(requirement);
            user.setUsername("RegimentCommander"+code+"@military.com");
            user.setPassword(enc.encode(password));
            List<Role> userRoles = user.getRoles();
            userRoles.add(roleRepository.findByRoleName(Constants.REGIMENTCOMMANDER));
            user.setRoles(userRoles);
            regiment.setCode(code);
            regiment.setRequirement(requirement);
            regiment.setType(typeRepository.findByTypeName(Constants.RECRUITS));
            regiment.setUser(user);
            regiment.setSupply(supply);
            supplyRepository.save(supply);
            userRepository.save(user);
            regimentRepository.save(regiment);
        }
        return regimentRegisterNotification;
    }

    @Override
    public void removeRegiment(int id) {
        regimentRepository.deleteById(id);
    }

    @Override
    public List<Regiment> showAll() {
        return regimentRepository.findAll();
    }
}
