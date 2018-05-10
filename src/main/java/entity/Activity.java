package entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int duration;
    private int staminaChange;
    private int strengthChange;
    private int intelligenceChange;
    private int medSkillChange;
    private int shootingChange;
    private int ammunitionCost;
    private int foodCost;
    private int equipmentCost;

    private String activityName;

    public Activity() {
    }

    public Activity(String activityName) {
        this.activityName = activityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedSkillChange() {
        return medSkillChange;
    }

    public void setMedSkillChange(int medSkillChange) {
        this.medSkillChange = medSkillChange;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStaminaChange() {
        return staminaChange;
    }

    public void setStaminaChange(int staminaChange) {
        this.staminaChange = staminaChange;
    }

    public int getStrengthChange() {
        return strengthChange;
    }

    public void setStrengthChange(int strengthChange) {
        this.strengthChange = strengthChange;
    }

    public int getIntelligenceChange() {
        return intelligenceChange;
    }

    public void setIntelligenceChange(int intelligenceChange) {
        this.intelligenceChange = intelligenceChange;
    }

    public int getShootingChange() {
        return shootingChange;
    }

    public void setShootingChange(int shootingChange) {
        this.shootingChange = shootingChange;
    }

    public int getAmmunitionCost() {
        return ammunitionCost;
    }

    public void setAmmunitionCost(int ammunitionCost) {
        this.ammunitionCost = ammunitionCost;
    }

    public int getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(int foodCost) {
        this.foodCost = foodCost;
    }

    public int getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(int equipmentCost) {
        this.equipmentCost = equipmentCost;
    }
}

