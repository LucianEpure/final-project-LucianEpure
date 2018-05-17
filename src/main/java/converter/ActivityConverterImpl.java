package converter;

import dto.ActivityDto;
import entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityConverterImpl implements  ActivityConverter{

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
