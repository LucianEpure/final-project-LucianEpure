package service;


import application.Constants;
import converter.RegimentConverter;
import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class RegimentServiceImpl implements RegimentService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RegimentRepository regimentRepository;
    private TypeRepository typeRepository;
    private RequirementRepository requirementRepository;
    private SupplyRepository supplyRepository;
    private RegimentConverter regimentConverter;
    private IValidator validator;


    @Autowired
    public RegimentServiceImpl(UserRepository userRepository, RoleRepository roleRepository, RegimentRepository regimentRepository, TypeRepository typeRepository, SupplyRepository supplyRepository, RequirementRepository requirementRepository, RegimentConverter regimentConverter){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.regimentRepository = regimentRepository;
        this.typeRepository = typeRepository;
        this.supplyRepository = supplyRepository;
        this.requirementRepository = requirementRepository;
        this.regimentConverter = regimentConverter;
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
            requirementRepository.save(requirement);
            supplyRepository.save(supply);
            userRepository.save(user);
            regimentRepository.save(regiment);
        }
        return regimentRegisterNotification;
    }

    @Override
    public void removeRegiment(int code) {
        Regiment regiment = regimentRepository.findByCode(code);
        regimentRepository.deleteById(regiment.getId());
    }

    @Override
    public RegimentDto findByCode(int code) {
        return regimentConverter.convertRegimentToDto(regimentRepository.findByCode(code));
    }

    @Override
    public void addMoreSupplies(SupplyDto supplyDto, int regimentCode) {
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Supply supply = regiment.getSupply();
        supply.setAmmunition(supply.getAmmunition()+supplyDto.getAmmunition());
        supply.setEquipment(supply.getEquipment()+supplyDto.getEquipment());
        supply.setFood(supply.getFood()+supplyDto.getFood());
        supplyRepository.save(supply);
    }

    @Override
    public SupplyDto findSupplies(int supplyId) {
        Supply supply = supplyRepository.getOne(supplyId);
        return regimentConverter.convertSupplyToDto(supply);
    }

    @Override
    public RequirementDto findRequirement(int requirementId) {
        Requirement requirement =requirementRepository.getOne(requirementId);
        return regimentConverter.convertRequirementToDto(requirement);
    }

    @Override
    public void changeRequirements(RequirementDto requirementDto, int regimentCode) {
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Requirement requirement = regiment.getRequirement();
        requirement.setRequiredIntelligence(requirementDto.getRequiredIntelligence());
        requirement.setRequiredMedSkills(requirementDto.getRequiredMedSkills());
        requirement.setRequiredShooting(requirementDto.getRequiredShooting());
        requirement.setRequiredStamina(requirementDto.getRequiredStamina());
        requirement.setRequiredStrength(requirementDto.getRequiredStrength());
        requirementRepository.save(requirement);
    }

    @Override
    public Type addNewType(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        return typeRepository.save(type);
    }

    @Override
    public List<RegimentDto> showAll() {
        List<Regiment> regiments = regimentRepository.findAll();
        List<RegimentDto> regimentDtos = new ArrayList<RegimentDto>();
        for(Regiment regiment:regiments){
            regimentDtos.add(regimentConverter.convertRegimentToDto(regiment));
        }
        return regimentDtos;
    }
}
