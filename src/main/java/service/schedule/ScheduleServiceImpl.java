package service.schedule;

import converter.ActivityConverter;
import converter.RegimentConverter;
import converter.ScheduleConverter;
import converter.SupplyConverter;
import dto.*;
import entity.Activity;
import entity.Regiment;
import entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActivityRepository;
import repository.RegimentRepository;
import repository.ScheduleRepository;
import service.regiment.RegimentService;
import service.regiment.SupplyService;
import validators.IValidator;
import validators.Notification;
import validators.ScheduleValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.Constants.APPROVED;
import static application.Constants.DENIED;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RegimentRepository regimentRepository;
    private RegimentService regimentService;
    private SupplyService supplyService;
    private ScheduleCRUDService scheduleCRUDService;
    private ScheduleRepository scheduleRepository;
    private ActivityRepository activityRepository;
    private ScheduleConverter scheduleConverter;
    private ScheduleReportService scheduleReportService;
    private ActivityConverter activityConverter;



    @Autowired
    public ScheduleServiceImpl(RegimentRepository regimentRepository, ScheduleRepository scheduleRepository, ScheduleConverter scheduleConverter, ActivityRepository activityRepository, ScheduleReportService scheduleReportService,RegimentService regimentService, ActivityConverter activityConverter,SupplyService supplyService, ScheduleCRUDService scheduleCRUDService){
        this.regimentRepository = regimentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleConverter = scheduleConverter;
        this.activityRepository = activityRepository;
        this.scheduleReportService = scheduleReportService;
        this.activityConverter = activityConverter;
        this.regimentService = regimentService;
        this.supplyService  = supplyService;
        this.scheduleCRUDService = scheduleCRUDService;
    }



@Override
public boolean checkIfApproved(ScheduleDto scheduleDto){
    List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regimentRepository.findByCode(scheduleDto.getRegimentCode()),scheduleDto.getDate());
    for(Schedule schedule1:schedules){
        if(schedule1.getApproved().equalsIgnoreCase(APPROVED))
            return true;
        else
            return false;
    }
    return false;
}

    @Override
    public ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activity){
        List<ActivityDto> activities = scheduleDto.getActivities();
        activities.add(activity);
        scheduleDto.setActivities(activities);
        scheduleDto.setScheduleReport(scheduleReportService.updateStats(scheduleDto.getScheduleReport(),activity,"add"));

        return scheduleDto;
    }
    @Override
    public ScheduleDto removeActivity(ScheduleDto scheduleDto){
        List<ActivityDto> activities = scheduleDto.getActivities();
        ActivityDto activity = activities.get(activities.size()-1);
        activities.remove(activities.size()-1);
        scheduleDto.setActivities(activities);
        scheduleDto.setScheduleReport(scheduleReportService.updateStats(scheduleDto.getScheduleReport(),activity,"remove"));
        return scheduleDto;
    }




    @Override
    public ActivityDto findActivityByName(String activityName){
        return activityConverter.convertActivityToDto(activityRepository.findByActivityName(activityName));
    }

    @Override
    public List<ScheduleDto> findByDate(Date date) {
        List<Schedule> schedules = scheduleRepository.findByDate(date);
        List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>();
        for(Schedule schedule:schedules){
            scheduleDtos.add(scheduleConverter.convertScheduleToDto(schedule));
        }
        return scheduleDtos;
    }

    @Override
    public void approveSchedule(int scheduleId) {
        ScheduleDto scheduleDto = scheduleCRUDService.findById(scheduleId);
        ScheduleReportDto scheduleReport = new ScheduleReportDto();
        RegimentDto regiment = regimentService.findByCode(scheduleDto.getRegimentCode());
        SupplyDto supply = supplyService.findSupplies(regiment.getSupplyId());
        scheduleReport.init(regimentService.findByCode(scheduleDto.getRegimentCode()),supply);
        scheduleDto.setApproved(APPROVED);
        for(ActivityDto activityDto:scheduleDto.getActivities()){
            scheduleReportService.updateStats(scheduleReport,activityDto,"add");
        }
        regimentService.update(scheduleReportService.retrieveRegiment(scheduleReport),scheduleReportService.retrieveSupply(scheduleReport));
        scheduleCRUDService.update(scheduleDto);
    }

    @Override
    public void denySchedule(int scheduleId) {
        ScheduleDto scheduleDto = scheduleCRUDService.findById(scheduleId);
        scheduleDto.setApproved(DENIED);
        scheduleCRUDService.update(scheduleDto);
    }
}
