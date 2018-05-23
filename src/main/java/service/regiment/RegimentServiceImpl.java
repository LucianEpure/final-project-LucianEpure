package service.regiment;


import application.Constants;
import converter.RegimentConverter;
import dto.RegimentDto;
import dto.SupplyDto;
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

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RegimentRepository regimentRepository;
    private TypeRepository typeRepository;
    private RequirementRepository requirementRepository;
    private SupplyRepository supplyRepository;
    private RegimentConverter regimentConverter;
    private RequestRepository requestRepository;
    private ScheduleRepository scheduleRepository;
    private IValidator validator;


    @Autowired
    public RegimentServiceImpl( RequestRepository requestRepository,UserRepository userRepository, RoleRepository roleRepository, RegimentRepository regimentRepository, TypeRepository typeRepository, SupplyRepository supplyRepository, RequirementRepository requirementRepository, RegimentConverter regimentConverter,ScheduleRepository scheduleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.regimentRepository = regimentRepository;
        this.typeRepository = typeRepository;
        this.supplyRepository = supplyRepository;
        this.requirementRepository = requirementRepository;
        this.regimentConverter = regimentConverter;
        this.requestRepository = requestRepository;
        this.scheduleRepository = scheduleRepository;
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
            requirementRepository.save(requirement);
            supplyRepository.save(supply);
            userRepository.save(user);
            regimentRepository.save(regiment);
        }
        return regimentRegisterNotification;
    }

    @Override
    @Transactional
    public Notification<Boolean> sendRegimentToWar(int regimentCode, int locationCode) {
       boolean found = false;
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Request request = requestRepository.getOne(locationCode);
        List<Type> types = request.getTypes();

            for(Type type:types){
                if(regiment.getType().getTypeName().equals(type.getTypeName())){
                    scheduleRepository.deleteByRegiment(regiment);
                    regimentRepository.deleteById(regiment.getId());
                    types.remove(type);
                    request.setTypes(types);
                    requestRepository.save(request);
                    if(types.size()==0)
                        requestRepository.deleteById(request.getId());

                    found = true;
                }

            }
            System.out.println("FOUUUUUUND"+found);
        validator = new WarValidator(found);
        boolean warValid = validator.validate();
        Notification<Boolean> sendToWarNotification = new Notification<>();
        if(!warValid){
            validator.getErrors().forEach(sendToWarNotification::addError);
            sendToWarNotification.setResult(Boolean.FALSE);
        }
        else
            sendToWarNotification.setResult(Boolean.TRUE);
        System.out.println("ERRRORRRRSS"+sendToWarNotification.getFormattedErrors());
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
        supplyRepository.save(supply);

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
