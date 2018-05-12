package dto;

import entity.Regiment;
import entity.Supply;

public class ScheduleReport {


    private int stamina;
    private int strength;
    private int intelligence;
    private int shooting;
    private int medSkills;
    private int code;
    private String typeName;

    private int ammunition;
    private int food;
    private int equipment;

    private int duration;


    public ScheduleReport init(Regiment regiment, Supply supply){

        this.stamina = regiment.getStamina();
        this.strength = regiment.getStrength();
        this.intelligence = regiment.getIntelligence();
        this.shooting = regiment.getShooting();
        this.medSkills = regiment.getMedSkills();
        this.code = regiment.getCode();
        this.typeName = regiment.getType().getTypeName();
        this.food = supply.getFood();
        this.ammunition = supply.getAmmunition();
        this.equipment = supply.getEquipment();
        this.duration = 0;
        return this;
    }

    public int getMedSkills() {
        return medSkills;
    }

    public void setMedSkills(int medSkills) {
        this.medSkills = medSkills;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }
}
