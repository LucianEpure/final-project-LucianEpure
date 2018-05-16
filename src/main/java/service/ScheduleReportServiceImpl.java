package service;

import application.Constants;
import dto.ActivityDto;
import dto.RegimentDto;
import dto.ScheduleReport;
import dto.SupplyDto;
import org.springframework.stereotype.Service;

import static application.Constants.*;

@Service
public class ScheduleReportServiceImpl implements ScheduleReportService {
    @Override
    public ScheduleReport updateStats(ScheduleReport scheduleReport, ActivityDto activity, String type) {
        if(type.equalsIgnoreCase("add")){
            scheduleReport.setMedSkills(scheduleReport.getMedSkills()+activity.getMedSkillChange());
            scheduleReport.setIntelligence(scheduleReport.getIntelligence()+activity.getIntelligenceChange());
            scheduleReport.setStrength(scheduleReport.getStrength()+activity.getStrengthChange());
            scheduleReport.setStamina(scheduleReport.getStamina()+activity.getStaminaChange());
            scheduleReport.setShooting(scheduleReport.getShooting()+activity.getShootingChange());
            scheduleReport.setAmmunition(scheduleReport.getAmmunition()-activity.getAmmunitionCost());
            scheduleReport.setFood(scheduleReport.getFood()-activity.getFoodCost());
            scheduleReport.setEquipment(scheduleReport.getEquipment()-activity.getEquipmentCost());
            scheduleReport.setDuration(scheduleReport.getDuration()+activity.getDuration());
            upgradeUnit(scheduleReport);
        }
        else if(type.equalsIgnoreCase("remove")){
            scheduleReport.setMedSkills(scheduleReport.getMedSkills()-activity.getMedSkillChange());
            scheduleReport.setIntelligence(scheduleReport.getIntelligence()-activity.getIntelligenceChange());
            scheduleReport.setStrength(scheduleReport.getStrength()-activity.getStrengthChange());
            scheduleReport.setStamina(scheduleReport.getStamina()-activity.getStaminaChange());
            scheduleReport.setShooting(scheduleReport.getShooting()+activity.getShootingChange());
            scheduleReport.setAmmunition(scheduleReport.getAmmunition()+activity.getAmmunitionCost());
            scheduleReport.setFood(scheduleReport.getFood()+activity.getFoodCost());
            scheduleReport.setEquipment(scheduleReport.getEquipment()+activity.getEquipmentCost());
            scheduleReport.setDuration(scheduleReport.getDuration()-activity.getDuration());
            downgradeUnit(scheduleReport);
        }

        return scheduleReport;
    }

    @Override
    public RegimentDto retrieveRegiment(ScheduleReport scheduleReport) {
        RegimentDto regimentDto = new RegimentDto();
        regimentDto.setCode(scheduleReport.getCode());
        regimentDto.setStrength(scheduleReport.getStrength());
        regimentDto.setStamina(scheduleReport.getStamina());
        regimentDto.setShooting(scheduleReport.getShooting());
        regimentDto.setIntelligence(scheduleReport.getIntelligence());
        regimentDto.setMedSkills(scheduleReport.getMedSkills());
        regimentDto.setTypeName(scheduleReport.getTypeName());
        return  regimentDto;
    }

    @Override
    public SupplyDto retrieveSupply(ScheduleReport scheduleReport) {
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setEquipment(scheduleReport.getEquipment());
        supplyDto.setAmmunition(scheduleReport.getAmmunition());
        supplyDto.setFood(scheduleReport.getFood());
        return  supplyDto;
    }

    private void upgradeUnit(ScheduleReport scheduleReport){
        if(scheduleReport.getTypeName().equalsIgnoreCase(RECRUITS)){
            if(scheduleReport.getMedSkills()>= MEDIC_UPGRADE_TRESHOLD)
                scheduleReport.setTypeName(MEDICS);
            else if (scheduleReport.getMedSkills()>= ASSAULT_UPGRADE_TRESHOLD)
                scheduleReport.setTypeName(ASSAULT);
            else if (scheduleReport.getMedSkills()>= ENGINEER_UPGRADE_THRESHOLD)
                scheduleReport.setTypeName(ENGINEERS);
            else if (scheduleReport.getMedSkills()>= INFANTRY_UPGRADE_THRESHOLD)
                scheduleReport.setTypeName(INFANTRY);
        }
    }
    private void downgradeUnit(ScheduleReport scheduleReport){
        if(scheduleReport.getTypeName().equalsIgnoreCase(MEDICS)&&scheduleReport.getMedSkills()<MEDIC_UPGRADE_TRESHOLD)
            scheduleReport.setTypeName(RECRUITS);
        else if(scheduleReport.getTypeName().equalsIgnoreCase(ASSAULT)&&scheduleReport.getMedSkills()<ASSAULT_UPGRADE_TRESHOLD)
            scheduleReport.setTypeName(RECRUITS);
        else if(scheduleReport.getTypeName().equalsIgnoreCase(ENGINEERS)&&scheduleReport.getMedSkills()<ENGINEER_UPGRADE_THRESHOLD)
            scheduleReport.setTypeName(RECRUITS);
        if(scheduleReport.getTypeName().equalsIgnoreCase(INFANTRY)&&scheduleReport.getMedSkills()<INFANTRY_UPGRADE_THRESHOLD)
            scheduleReport.setTypeName(RECRUITS);

    }
}
