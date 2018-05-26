package service.schedule;

import converter.activity.ActivityConverter;
import converter.regiment.RegimentConverter;
import converter.schedule.ScheduleConverter;
import converter.regiment.SupplyConverter;
import converter.schedule.ScheduleReportConverter;
import dto.*;
import entity.Activity;
import entity.Regiment;
import entity.Schedule;
import entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RegimentRepository;
import repository.ScheduleRepository;
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
    private ScheduleReportConverter scheduleReportConverter;


    @Autowired
    public ScheduleCRUDServiceImpl(ScheduleReportConverter scheduleReportConverter, SupplyConverter supplyConverter,ActivityConverter activityConverter,ScheduleRepository scheduleRepository, RegimentRepository regimentRepository, RegimentConverter regimentConverter,ScheduleConverter scheduleConverter){
        this.scheduleRepository = scheduleRepository;
        this.regimentConverter = regimentConverter;
        this.regimentRepository = regimentRepository;
        this.activityConverter = activityConverter;
        this.scheduleConverter = scheduleConverter;
        this.supplyConverter = supplyConverter;
        this.scheduleReportConverter = scheduleReportConverter;

    }
    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        Regiment regiment;
        Supply supply;
        schedule.setDate(scheduleDto.getDate());
        regiment = regimentRepository.findByCode(scheduleDto.getRegimentCode());
        supply = regiment.getSupply();
        List<Schedule> schedules = scheduleRepository.findByRegimentAndDate(regiment,scheduleDto.getDate());
        for(Schedule schedule1:schedules){
            schedule.setId(schedule1.getId());      //if there is already a schedule for that date that has not been approved, the commander may update it
        }
        schedule.setRegiment(regiment);
        schedule = scheduleRepository.save(schedule);
        ScheduleDto scheduleDto1 = scheduleConverter.convertScheduleToDto(schedule);
        RegimentDto regimentDto = regimentConverter.convertRegimentToDto(regiment);
        SupplyDto supplyDto = supplyConverter.convertSupplyToDto(supply);
        scheduleDto1.setScheduleReport(scheduleReportConverter.convertToScheduleReport(regimentDto,supplyDto));
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
            schedule.setRegiment(regimentRepository.findByCode(scheduleDto.getRegimentCode()));
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
