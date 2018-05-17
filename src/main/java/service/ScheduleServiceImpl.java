package service;

import converter.ActivityConverter;
import converter.RegimentConverter;
import converter.ScheduleConverter;
import converter.SupplyConverter;
import dto.*;
import entity.Activity;
import entity.Schedule;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActivityRepository;
import repository.RegimentRepository;
import repository.ScheduleRepository;
import validators.IValidator;
import validators.Notification;
import validators.ScheduleValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.Constants.APPROVED;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RegimentRepository regimentRepository;
    private RegimentService regimentService;
    private ScheduleRepository scheduleRepository;
    private ActivityRepository activityRepository;
    private ScheduleConverter scheduleConverter;
    private ScheduleReportService scheduleReportService;
    private ActivityConverter activityConverter;
    private RegimentConverter regimentConverter;
    private SupplyConverter supplyConverter;


    @Autowired
    public ScheduleServiceImpl(RegimentRepository regimentRepository, ScheduleRepository scheduleRepository, ScheduleConverter scheduleConverter, ActivityRepository activityRepository, ScheduleReportService scheduleReportService,RegimentConverter regimentConverter, SupplyConverter supplyConverter, RegimentService regimentService, ActivityConverter activityConverter){
        this.regimentRepository = regimentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleConverter = scheduleConverter;
        this.activityRepository = activityRepository;
        this.scheduleReportService = scheduleReportService;
        this.activityConverter = activityConverter;
        this.regimentService = regimentService;
        this.regimentConverter = regimentConverter;
        this.supplyConverter = supplyConverter;
    }

    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {



        Schedule schedule = new Schedule();
       // List<Schedule> schedules = scheduleRepository.findByRegiment(regimentRepository.findByCode(scheduleDto.getRegimentCode()));

        List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regimentRepository.findByCode(scheduleDto.getRegimentCode()),scheduleDto.getDate());

        System.out.println("HEEEEEEEEEEI"+schedules.size());
        schedule.setDate(scheduleDto.getDate());
        for(Schedule schedule1:schedules){
                schedule.setId(schedule1.getId());
                System.out.println("AM SChIMBAAAAAAAAAAAAAAT");

        }
        schedule.setRegiment(regimentRepository.findByCode(scheduleDto.getRegimentCode()));
        schedule = scheduleRepository.save(schedule);
        ScheduleDto scheduleDto1 = scheduleConverter.convertScheduleToDto(schedule);
        ScheduleReportDto scheduleReport = new ScheduleReportDto();
        scheduleReport.init(regimentConverter.convertRegimentToDto(schedule.getRegiment()),supplyConverter.convertSupplyToDto(schedule.getRegiment().getSupply()));
        scheduleDto1.setScheduleReport(scheduleReport);
        return  scheduleDto1;
    }

@Override
public boolean checkIfApproved(ScheduleDto scheduleDto){
    List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regimentRepository.findByCode(scheduleDto.getRegimentCode()),scheduleDto.getDate());
    for(Schedule schedule1:schedules){
        if(schedule1.getApproved().equalsIgnoreCase(APPROVED))
            return true;
    }
    return false;
}

    @Override
    public ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activity){
        List<ActivityDto> activities = scheduleDto.getActivities();
        activities.add(activity);
        scheduleDto.setActivities(activities);
        //scheduleDto.setActivityNames(scheduleDto.getActivityNames()+" "+activity.getActivityName());
        scheduleDto.setScheduleReport(scheduleReportService.updateStats(scheduleDto.getScheduleReport(),activity,"add"));
        return scheduleDto;
    }
    @Override
    public ScheduleDto removeActivity(ScheduleDto scheduleDto){
        List<ActivityDto> activities = scheduleDto.getActivities();
        ActivityDto activity = activities.get(activities.size()-1);
        activities.remove(activities.size()-1);
        scheduleDto.setActivities(activities);
       // RegimentDto regimentStats = updateStats(scheduleDto.getRegimentStats(),activity,"remove");
        scheduleDto.setScheduleReport(scheduleReportService.updateStats(scheduleDto.getScheduleReport(),activity,"remove"));
        return scheduleDto;
    }

    @Override
    public Notification<Boolean> update(ScheduleDto scheduleDto) {

        IValidator validator = new ScheduleValidator(scheduleDto);
        boolean scheduleValid = validator.validate();
        Notification<Boolean> scheduleNotification = new Notification<Boolean>();
        if(!scheduleValid){
            validator.getErrors().forEach(scheduleNotification::addError );
            scheduleNotification.setResult(Boolean.FALSE);
        }
        else{
            Schedule schedule = new Schedule();
            schedule.setId(scheduleDto.getId());
            schedule.setRegiment(regimentRepository.getOne(scheduleDto.getRegimentId()));
            schedule.setDate(scheduleDto.getDate());
            schedule.setApproved(scheduleDto.getApproved());
            List<Activity> activities = new ArrayList<Activity>();
            for(ActivityDto activityDto:scheduleDto.getActivities()){
                activities.add(activityConverter.convertActivityFromDto(activityDto));
            }
            schedule.setActivities(activities);

            RegimentDto regimentDto = scheduleReportService.retrieveRegiment(scheduleDto.getScheduleReport());
            SupplyDto supplyDto = scheduleReportService.retrieveSupply(scheduleDto.getScheduleReport());


            System.out.println("ACTIVITIESSS"+schedule.getActivities().get(0).getActivityName());
            System.out.println("APPROV"+schedule.getApproved());
            scheduleRepository.save(schedule);
            //regimentService.update(regimentDto,supplyDto);
            scheduleNotification.setResult(Boolean.TRUE);
        }

        return scheduleNotification;
    }

    @Override
    public List<ScheduleDto> showAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>();
        for(Schedule schedule:schedules){
            scheduleDtos.add(scheduleConverter.convertScheduleToDto(schedule));
        }
        return scheduleDtos;
    }

    @Override
    public ScheduleDto findById(int id) {
        return scheduleConverter.convertScheduleToDto(scheduleRepository.getOne(id));
    }


    @Override
    public ActivityDto findActivityByName(String activityName){
        return activityConverter.convertActivityToDto(activityRepository.findByActivityName(activityName));
    }
/*
    @Override
    public List<ScheduleDto> findByRegimentAndDate(int code, Date date) {
        List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regimentRepository.findByCode(code),date);
        List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>();
        for(Schedule schedule:schedules){
            scheduleDtos.add(scheduleConverter.convertScheduleToDto(schedule));
        }
        return scheduleDtos;    }
*/
    @Override
    public List<ScheduleDto> findByDate(Date date) {
        List<Schedule> schedules = scheduleRepository.findByDate(date);
        List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>();
        for(Schedule schedule:schedules){
            scheduleDtos.add(scheduleConverter.convertScheduleToDto(schedule));
        }
        return scheduleDtos;
    }
}
