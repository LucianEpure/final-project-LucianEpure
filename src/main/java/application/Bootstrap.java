package application;

import converter.TypeConverter;
import dto.RegimentDto;
import dto.RequestDto;
import dto.TypeDto;
import dto.UserDto;
import entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ActivityRepository;
import repository.RoleRepository;
import repository.TypeRepository;
import service.regiment.RegimentService;
import service.regiment.SupplyService;
import service.request.RequestService;
import service.schedule.ScheduleService;
import service.regiment.UserService;

import javax.annotation.PostConstruct;


import java.util.ArrayList;
import java.util.List;

import static application.Constants.*;


@Component
public class Bootstrap {
    private RoleRepository roleRepository;

    private RegimentService regimentService;
    private TypeConverter typeConverter;
    private SupplyService supplyService;
    private UserService userService;
    private ActivityRepository activityRepository;
    private TypeRepository typeRepository;
    private RequestService requestService;
    @Autowired
    public Bootstrap(TypeConverter typeConverter, RequestService requestService, RoleRepository roleRepository,TypeRepository typeRepository, UserService userService, RegimentService regimentService, ActivityRepository activityRepository,SupplyService supplyService ){
        this.roleRepository = roleRepository;
        this.regimentService = regimentService;
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.supplyService = supplyService;
        this.typeRepository = typeRepository;
        this.requestService = requestService;
        this.typeConverter = typeConverter;

    }

    @PostConstruct
    private void initialize(){
        initRoles();
        initTypes();
        initActivities();
        initUsers();
        initRequests();

    }

    private void initRoles() {
        Role quartermaster = new Role(Constants.QUARTERMASTER);
        Role regimentCommander = new Role(Constants.REGIMENTCOMMANDER);
        Role chiefCommander = new Role(Constants.CHIEFCOMMANDER);
        roleRepository.save(quartermaster);
        roleRepository.save(chiefCommander);
        roleRepository.save(regimentCommander);
    }

    private void initTypes(){

        regimentService.addNewType(INFANTRY);
        regimentService.addNewType(MEDICS);
        regimentService.addNewType(ASSAULT);
        regimentService.addNewType(ENGINEERS);
        regimentService.addNewType(RECRUITS);
    }

    private void initActivities(){

        Setup setup = new Setup();
        Activity eating = new Activity(EATING);
        Activity sleeping = new Activity(SLEEPING);
        Activity medTraining = new Activity(MEDTRAINING);
        Activity shootTraining = new Activity(SHOOTTRAINING);
        Activity strengthTraining = new Activity(STRENGTHTRAINING);
        Activity tacticsTraining = new Activity(TACTICSTRAINING);

        activityRepository.save(setup.setupActivity(eating,2,20,5,0,0,0,50,0,0));
        activityRepository.save(setup.setupActivity(sleeping,8,60,5,0,2,0,0,0,0));
        activityRepository.save(setup.setupActivity(medTraining,2,-15,0,0,5,20,0,0,35));
        activityRepository.save(setup.setupActivity(shootTraining,2,-15,10,30,0,0,0,100,10));
        activityRepository.save(setup.setupActivity(strengthTraining,3,-40,40,0,0,0,0,0,10));
        activityRepository.save(setup.setupActivity(tacticsTraining,2,-10,0,0,20,0,0,0,5));


    }

    private void initUsers(){
        UserDto user1 = new UserDto();
        user1.setUsername("QM1@yahoo.com");
        user1.setPassword("aB123456!");
        userService.registerUser(user1,QUARTERMASTER);
        UserDto user2 = new UserDto();
        user2.setUsername("ChiefCommander1@yahoo.com");
        user2.setPassword("aB123456!");
        userService.registerUser(user2,CHIEFCOMMANDER);
        regimentService.enlistRegiment(123456,"aB123456!");
        regimentService.enlistRegiment(333333,"aB123456!");
        regimentService.enlistRegiment(444444,"aB123456!");
        RegimentDto regimentDto = regimentService.findByCode(333333);
        regimentDto.setShooting(290);
        regimentService.update(regimentDto,supplyService.findSupplies(regimentDto.getSupplyId()));
        RegimentDto regimentDto1 = regimentService.findByCode(444444);
        regimentDto1.setStrength(390);
        regimentService.update(regimentDto1,supplyService.findSupplies(regimentDto1.getSupplyId()));
        //ScheduleDto scheduleDto = new ScheduleDto();

       // scheduleDto.setDate(new Date());
       // scheduleService.save(scheduleDto, 123456);

    }

    private void initRequests(){
        RequestDto requestDto = new RequestDto();
        List<TypeDto> types = new ArrayList<TypeDto>();
        types.add(typeConverter.convertToDto(typeRepository.findByTypeName(INFANTRY)));
        types.add(typeConverter.convertToDto(typeRepository.findByTypeName(INFANTRY)));
        types.add(typeConverter.convertToDto(typeRepository.findByTypeName(MEDICS)));
        requestDto.setTypes(types);
        requestDto.setLocationName("Guantanamo");
        requestService.addRequest(requestDto);
        List<RequestDto> requestDtos  = requestService.showAll();

    }

}


