package application;

import entity.Activity;

public class Setup {


    protected Activity setupActivity(Activity activity, int duration, int staminaChange, int strengthChange, int shootChange, int intelligenceChange, int medSkillChange, int foodCost, int ammoCost, int equipmentCost){

        activity.setDuration(duration);
        activity.setAmmunitionCost(ammoCost);
        activity.setEquipmentCost(equipmentCost);
        activity.setFoodCost(foodCost);
        activity.setIntelligenceChange(intelligenceChange);
        activity.setShootingChange(shootChange);
        activity.setStaminaChange(staminaChange);
        activity.setStrengthChange(strengthChange);
        activity.setMedSkillChange(medSkillChange);
        return activity;
    }
}
