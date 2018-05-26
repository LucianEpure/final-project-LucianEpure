package service.schedule;

import converter.activity.ActivityConverter;
import converter.regiment.RegimentConverter;
import converter.regiment.SupplyConverter;
import converter.schedule.ScheduleConverter;
import converter.schedule.ScheduleReportConverter;
import dto.*;
import entity.Regiment;
import entity.Schedule;
import entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActivityRepository;
import repository.RegimentRepository;
import repository.ScheduleRepository;
import repository.SupplyRepository;
import service.regiment.RegimentService;
import service.regiment.SupplyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.Constants.APPROVED;
import static application.Constants.DENIED;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RegimentRepository regimentRepository;
    private RegimentService regimentService;
    private ScheduleCRUDService scheduleCRUDService;
    private ScheduleRepository scheduleRepository;
    private ActivityRepository activityRepository;
    private ScheduleConverter scheduleConverter;
    private ScheduleReportService scheduleReportService;
    private ActivityConverter activityConverter;
    private ScheduleReportConverter scheduleReportConverter;
    private RegimentConverter regimentConverter;
    private SupplyConverter supplyConverter;



    @Autowired
    public ScheduleServiceImpl( SupplyConverter supplyConverter, RegimentConverter regimentConverter, ScheduleReportConverter scheduleReportConverter, RegimentRepository regimentRepository, ScheduleRepository scheduleRepository, ScheduleConverter scheduleConverter, ActivityRepository activityRepository, ScheduleReportService scheduleReportService, RegimentService regimentService, ActivityConverter activityConverter,  ScheduleCRUDService scheduleCRUDService){
        this.regimentRepository = regimentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleConverter = scheduleConverter;
        this.activityRepository = activityRepository;
        this.scheduleReportService = scheduleReportService;
        this.activityConverter = activityConverter;
        this.regimentService = regimentService;
        this.regimentConverter = regimentConverter;
        this.scheduleCRUDService = scheduleCRUDService;
        this.scheduleReportConverter = scheduleReportConverter;
        this.supplyConverter = supplyConverter;

    }



@Override
public boolean checkIfApproved(ScheduleDto scheduleDto){
        Regiment regiment = regimentRepository.findByCode(scheduleDto.getRegimentCode());
        List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regiment,scheduleDto.getDate());
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
    public void approveSchedule(ScheduleDto scheduleDto) {
        ScheduleReportDto scheduleReport = new ScheduleReportDto();
        Regiment regiment = regimentRepository.findByCode(scheduleDto.getRegimentCode());
        Supply supply = regiment.getSupply();
        RegimentDto regimentDto = regimentConverter.convertRegimentToDto(regiment);
        SupplyDto supplyDto = supplyConverter.convertSupplyToDto(supply);
        scheduleReport = scheduleReportConverter.convertToScheduleReport(regimentDto,supplyDto);
        scheduleDto.setApproved(APPROVED);
        for(ActivityDto activityDto:scheduleDto.getActivities()){
            scheduleReportService.updateStats(scheduleReport,activityDto,"add");
        }
        regimentDto = scheduleReportConverter.extractRegiment(scheduleReport);
        supplyDto = scheduleReportConverter.extractSupply(scheduleReport);
        regimentService.update(regimentDto,supplyDto);
        scheduleCRUDService.update(scheduleDto);
    }

    @Override
    public void denySchedule(int scheduleId) {
        ScheduleDto scheduleDto = scheduleCRUDService.findById(scheduleId);
        scheduleDto.setApproved(DENIED);
        scheduleCRUDService.update(scheduleDto);
    }
}
