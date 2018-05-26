package service.regiment;


import application.Constants;
import converter.regiment.RegimentConverter;
import dto.RegimentDto;
import dto.RequestDto;
import dto.SupplyDto;
import dto.TypeDto;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.*;
import service.request.RequestService;
import validators.IValidator;
import validators.Notification;
import validators.RegimentValidator;
import validators.WarValidator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static application.Constants.MAIL;
import static application.Constants.USERNAME;

@Service
public class RegimentServiceImpl implements RegimentService {


    private RoleRepository roleRepository;
    private RegimentRepository regimentRepository;
    private TypeRepository typeRepository;
    private RegimentConverter regimentConverter;
    private RequestService requestService;
    private ScheduleRepository scheduleRepository;
    private IValidator validator;


    @Autowired
    public RegimentServiceImpl( RequestService requestService,  RoleRepository roleRepository, RegimentRepository regimentRepository, TypeRepository typeRepository, RegimentConverter regimentConverter,ScheduleRepository scheduleRepository){

        this.roleRepository = roleRepository;
        this.regimentRepository = regimentRepository;
        this.typeRepository = typeRepository;
        this.regimentConverter = regimentConverter;
        this.scheduleRepository = scheduleRepository;
        this.requestService = requestService;
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
            user.setUsername(USERNAME+code+MAIL);
            user.setPassword(enc.encode(password));
            List<Role> userRoles = user.getRoles();
            userRoles.add(roleRepository.findByRoleName(Constants.REGIMENTCOMMANDER));
            user.setRoles(userRoles);
            regiment.setCode(code);
            regiment.setRequirement(requirement);
            regiment.setType(typeRepository.findByTypeName(Constants.RECRUITS));
            regiment.setUser(user);
            regiment.setSupply(supply);
            regimentRepository.save(regiment);
        }
        return regimentRegisterNotification;
    }

    @Override
    @Transactional
    public Notification<Boolean> sendRegimentToWar(int regimentCode, RequestDto requestDto) {
       boolean found = false;
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        List<TypeDto> types = requestDto.getTypes();
            for(TypeDto type:types){
                if(regiment.getType().getTypeName().equals(type.getTypeName())){
                    scheduleRepository.deleteByRegiment(regiment);
                    regimentRepository.deleteById(regiment.getId());
                    types.remove(type);
                    requestDto.setTypes(types);
                    requestService.updateRequest(requestDto);
                    found = true;
                    break;
                }

            }
        validator = new WarValidator(found);
        boolean warValid = validator.validate();
        Notification<Boolean> sendToWarNotification = new Notification<>();
        if(!warValid){
            validator.getErrors().forEach(sendToWarNotification::addError);
            sendToWarNotification.setResult(Boolean.FALSE);
        }
        else
            sendToWarNotification.setResult(Boolean.TRUE);
        return sendToWarNotification;
    }

    @Override
    public RegimentDto findByCode(int regimentCode) {
        return regimentConverter.convertRegimentToDto(regimentRepository.findByCode(regimentCode));
    }

    @Override
    public RegimentDto update(RegimentDto regimentDto, SupplyDto supplyDto) {
        int regimentCode = regimentDto.getCode();
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Supply supply = regiment.getSupply();


        supply.setFood(supplyDto.getFood());
        supply.setAmmunition(supplyDto.getAmmunition());
        supply.setEquipment(supplyDto.getEquipment());

        regiment.setStamina(regimentDto.getStamina());
        regiment.setStrength(regimentDto.getStrength());
        regiment.setShooting(regimentDto.getShooting());
        regiment.setIntelligence(regimentDto.getIntelligence());
        regiment.setMedSkills(regimentDto.getMedSkills());
        regiment.setType(typeRepository.findByTypeName(regimentDto.getTypeName()));
        regiment.setSupply(supply);
        return regimentConverter.convertRegimentToDto(regimentRepository.save(regiment));

    }




    @Override
    public int extractRegimentFromUser(String username) {
        return Integer.parseInt(username.replaceAll("[^0-9]",""));
    }

    @Override
    public void removeAll() {
        regimentRepository.deleteAll();
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
