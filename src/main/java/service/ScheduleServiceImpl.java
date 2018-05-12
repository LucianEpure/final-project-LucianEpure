package service;

import converter.ScheduleConverter;
import dto.ActivityDto;
import dto.RegimentDto;
import dto.ScheduleDto;
import dto.ScheduleReport;
import entity.Activity;
import entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ActivityRepository;
import repository.RegimentRepository;
import repository.ScheduleRepository;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private RegimentRepository regimentRepository;
    private ScheduleRepository scheduleRepository;
    private ActivityRepository activityRepository;
    private ScheduleConverter scheduleConverter;


    @Autowired
    public ScheduleServiceImpl(RegimentRepository regimentRepository, ScheduleRepository scheduleRepository, ScheduleConverter scheduleConverter, ActivityRepository activityRepository){
        this.regimentRepository = regimentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleConverter = scheduleConverter;
        this.activityRepository = activityRepository;
    }

    @Override
    public ScheduleDto save(ScheduleDto scheduleDto, int regimentCode) {

        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDto.getDate());
        schedule.setRegiment(regimentRepository.findByCode(regimentCode));
        schedule = scheduleRepository.save(schedule);
        ScheduleDto scheduleDto1 = scheduleConverter.convertScheduleToDto(schedule);
        ScheduleReport scheduleReport = new ScheduleReport();
        scheduleReport.init(schedule.getRegiment(),schedule.getRegiment().getSupply());
        scheduleDto1.setScheduleReport(scheduleReport);
        return  scheduleDto1;
    }

    public ScheduleReport updateStats(ScheduleReport scheduleReport, ActivityDto activity){

        scheduleReport.setMedSkills(scheduleReport.getMedSkills()+activity.getMedSkillChange());
        scheduleReport.setIntelligence(scheduleReport.getIntelligence()+activity.getIntelligenceChange());
        scheduleReport.setStrength(scheduleReport.getStrength()+activity.getStrengthChange());
        scheduleReport.setStamina(scheduleReport.getStamina()+activity.getStaminaChange());
        scheduleReport.setShooting(scheduleReport.getShooting()+activity.getShootingChange());
        scheduleReport.setAmmunition(scheduleReport.getAmmunition()-activity.getAmmunitionCost());
        scheduleReport.setFood(scheduleReport.getFood()-activity.getFoodCost());
        scheduleReport.setEquipment(scheduleReport.getEquipment()-activity.getEquipmentCost());
        return scheduleReport;
    }
    @Override
    public ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activity){
        List<ActivityDto> activities = scheduleDto.getActivities();
        activities.add(activity);
        scheduleDto.setActivities(activities);
       // RegimentDto regimentStats = updateStats(scheduleDto.getRegimentStats(),activity);
        scheduleDto.setScheduleReport(updateStats(scheduleDto.getScheduleReport(),activity));
        return scheduleDto;
    }
    @Override
    public ScheduleDto removeActivity(ScheduleDto scheduleDto){
        List<ActivityDto> activities = scheduleDto.getActivities();
        ActivityDto activity = activities.get(activities.size()-1);
        activities.remove(activities.size()-1);
        scheduleDto.setActivities(activities);
       // RegimentDto regimentStats = updateStats(scheduleDto.getRegimentStats(),activity);
        //scheduleDto.setRegimentStats(regimentStats);
        return scheduleDto;
    }

    @Override
    public ScheduleDto update(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setRegiment(regimentRepository.getOne(scheduleDto.getRegimentId()));
        schedule.setDate(scheduleDto.getDate());
        List<Activity> activities = new ArrayList<Activity>();
        for(ActivityDto activityDto:scheduleDto.getActivities()){
            activities.add(scheduleConverter.convertActivityFromDto(activityDto));
        }
        schedule.setActivities(activities);
        return scheduleConverter.convertScheduleToDto(scheduleRepository.save(schedule));
    }


    @Override
    public ActivityDto findActivityByName(String activityName){
        return scheduleConverter.convertActivityToDto(activityRepository.findByActivityName(activityName));
    }
}
