package service;

import converter.ScheduleConverter;
import dto.*;
import entity.Activity;
import entity.Regiment;
import entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActivityRepository;
import repository.RegimentRepository;
import repository.ScheduleRepository;
import validators.IValidator;
import validators.Notification;
import validators.ScheduleValidator;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RegimentRepository regimentRepository;
    private RegimentService regimentService;
    private ScheduleRepository scheduleRepository;
    private ActivityRepository activityRepository;
    private ScheduleConverter scheduleConverter;
    private ScheduleReportService scheduleReportService;


    @Autowired
    public ScheduleServiceImpl(RegimentRepository regimentRepository, ScheduleRepository scheduleRepository, ScheduleConverter scheduleConverter, ActivityRepository activityRepository, ScheduleReportService scheduleReportService, RegimentService regimentService){
        this.regimentRepository = regimentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleConverter = scheduleConverter;
        this.activityRepository = activityRepository;
        this.scheduleReportService = scheduleReportService;
        this.regimentService = regimentService;
    }

    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {

        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDto.getDate());
        schedule.setRegiment(regimentRepository.findByCode(scheduleDto.getRegimentCode()));
        schedule = scheduleRepository.save(schedule);
        ScheduleDto scheduleDto1 = scheduleConverter.convertScheduleToDto(schedule);
        ScheduleReport scheduleReport = new ScheduleReport();
        scheduleReport.init(schedule.getRegiment(),schedule.getRegiment().getSupply());
        scheduleDto1.setScheduleReport(scheduleReport);
        return  scheduleDto1;
    }




    @Override
    public ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activity){
        List<ActivityDto> activities = scheduleDto.getActivities();
        activities.add(activity);
        scheduleDto.setActivities(activities);
       // RegimentDto regimentStats = updateStats(scheduleDto.getRegimentStats(),activity);
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
            List<Activity> activities = new ArrayList<Activity>();
            for(ActivityDto activityDto:scheduleDto.getActivities()){
                activities.add(scheduleConverter.convertActivityFromDto(activityDto));
            }
            schedule.setActivities(activities);

            RegimentDto regimentDto = scheduleReportService.retrieveRegiment(scheduleDto.getScheduleReport());
            SupplyDto supplyDto = scheduleReportService.retrieveSupply(scheduleDto.getScheduleReport());

            scheduleRepository.save(schedule);
            regimentService.update(regimentDto,supplyDto);
            scheduleNotification.setResult(Boolean.TRUE);
        }

        return scheduleNotification;
    }


    @Override
    public ActivityDto findActivityByName(String activityName){
        return scheduleConverter.convertActivityToDto(activityRepository.findByActivityName(activityName));
    }
}
