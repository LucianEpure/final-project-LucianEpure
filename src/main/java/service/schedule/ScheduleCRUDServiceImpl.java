package service.schedule;

import converter.ActivityConverter;
import converter.RegimentConverter;
import converter.ScheduleConverter;
import converter.SupplyConverter;
import dto.ActivityDto;
import dto.ScheduleDto;
import dto.ScheduleReportDto;
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
import java.util.List;

@Service
public class ScheduleCRUDServiceImpl implements ScheduleCRUDService {
    private ScheduleRepository scheduleRepository;
    private RegimentRepository regimentRepository;
    private ScheduleConverter scheduleConverter;
    private ActivityConverter activityConverter;
    private RegimentConverter regimentConverter;
    private SupplyConverter supplyConverter;


    @Autowired
    public ScheduleCRUDServiceImpl(SupplyConverter supplyConverter,ActivityConverter activityConverter,ScheduleRepository scheduleRepository, RegimentRepository regimentRepository, RegimentConverter regimentConverter,ScheduleConverter scheduleConverter){
        this.scheduleRepository = scheduleRepository;
        this.regimentConverter = regimentConverter;
        this.regimentRepository = regimentRepository;
        this.activityConverter = activityConverter;
        this.scheduleConverter = scheduleConverter;
        this.supplyConverter = supplyConverter;

    }
    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDto.getDate());
        List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regimentRepository.findByCode(scheduleDto.getRegimentCode()),scheduleDto.getDate());
        for(Schedule schedule1:schedules){
            schedule.setId(schedule1.getId());      //if there is already a schedule for that date that has not been approved, the commander may update it
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
            scheduleRepository.save(schedule);
            scheduleNotification.setResult(Boolean.TRUE);
        }

        return scheduleNotification;
    }

    @Override
    public ScheduleDto findById(int id) {
        return scheduleConverter.convertScheduleToDto(scheduleRepository.getOne(id));
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
    public void removeSchedulesOfRegiment(int regimentCode) {
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        scheduleRepository.deleteByRegiment(regiment);
    }

}
