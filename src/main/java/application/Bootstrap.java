package application;

import dto.UserDto;
import entity.Activity;
import entity.Role;
import entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ActivityRepository;
import repository.RoleRepository;
import repository.TypeRepository;
import repository.UserRepository;
import service.RegimentService;

import javax.annotation.PostConstruct;


import static application.Constants.*;


@Component
public class Bootstrap {
    private RoleRepository roleRepository;
    private TypeRepository typeRepository;
    private RegimentService regimentService;
    private RegimentService userService;
    private UserRepository userRepository;
    private ActivityRepository activityRepository;


    @Autowired
    public Bootstrap(RoleRepository roleRepository, RegimentService userService, TypeRepository typeRepository, RegimentService regimentService, UserRepository userRepository, ActivityRepository activityRepository ){
        this.roleRepository = roleRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.regimentService = regimentService;
        this.activityRepository = activityRepository;
        this.userService = userService;

    }

    @PostConstruct
    private void initialize(){
        initRoles();
        initTypes();
        initActivities();
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
        userService.registerAdmin(user1);
        regimentService.enlistRegiment(123456,"aB123456!");

    }

}


