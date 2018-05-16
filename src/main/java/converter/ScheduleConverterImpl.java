package converter;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import entity.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConverterImpl implements ScheduleConverter{

    RegimentConverter regimentConverter;

    public ScheduleConverterImpl(RegimentConverter regimentConverter){
        this.regimentConverter = regimentConverter;
    }
    @Override
    public ScheduleDto convertScheduleToDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDate(schedule.getDate());
        scheduleDto.setRegimentId(schedule.getRegiment().getId());
        //scheduleDto.setRegimentStats(regimentConverter.convertRegimentToDto(schedule.getRegiment()));
        //System.out.println("REGIMEEEEEEEEEENT"+ scheduleDto.getRegimentStats().getTypeName());

        return scheduleDto;
    }

    @Override
    public ActivityDto convertActivityToDto(Activity activity) {
        ActivityDto activityDto = new ActivityDto() ;
        activityDto.setId(activity.getId());
        activityDto.setActivityName(activity.getActivityName());
        activityDto.setAmmunitionCost(activity.getAmmunitionCost());
        activityDto.setDuration(activity.getDuration());
        activityDto.setEquipmentCost(activity.getEquipmentCost());
        activityDto.setFoodCost(activity.getFoodCost());
        activityDto.setIntelligenceChange(activity.getIntelligenceChange());
        activityDto.setMedSkillChange(activity.getMedSkillChange());
        activityDto.setShootingChange(activity.getShootingChange());
        activityDto.setStaminaChange(activity.getStaminaChange());
        activityDto.setStrengthChange(activity.getStrengthChange());
        return activityDto;
    }

    @Override
    public Activity convertActivityFromDto(ActivityDto activityDto) {
        Activity activity = new Activity() ;
        activity.setId(activityDto.getId());
        activity.setActivityName(activityDto.getActivityName());
        activity.setAmmunitionCost(activityDto.getAmmunitionCost());
        activity.setDuration(activityDto.getDuration());
        activity.setEquipmentCost(activityDto.getEquipmentCost());
        activity.setFoodCost(activityDto.getFoodCost());
        activity.setIntelligenceChange(activityDto.getIntelligenceChange());
        activity.setMedSkillChange(activityDto.getMedSkillChange());
        activity.setShootingChange(activityDto.getShootingChange());
        activity.setStaminaChange(activityDto.getStaminaChange());
        activity.setStrengthChange(activityDto.getStrengthChange());
        return activity;
    }
}
