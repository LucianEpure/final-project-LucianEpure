package converter.schedule;

import dto.RegimentDto;
import dto.ScheduleReportDto;
import dto.SupplyDto;
import org.springframework.stereotype.Component;

@Component
public class ScheduleReportConverterImpl implements  ScheduleReportConverter{
    @Override
    public ScheduleReportDto convertToScheduleReport(RegimentDto regimentDto, SupplyDto supplyDto) {
        ScheduleReportDto scheduleReportDto = new ScheduleReportDto();
        scheduleReportDto.setStamina(regimentDto.getStamina());
        scheduleReportDto.setStrength(regimentDto.getStrength());
        scheduleReportDto.setIntelligence(regimentDto.getIntelligence());
        scheduleReportDto.setShooting(regimentDto.getShooting());
        scheduleReportDto.setMedSkills(regimentDto.getMedSkills());
        scheduleReportDto.setCode( regimentDto.getCode());
        scheduleReportDto.setTypeName(regimentDto.getTypeName());
        scheduleReportDto.setTypeId(regimentDto.getTypeId());
        scheduleReportDto.setFood( supplyDto.getFood());
        scheduleReportDto.setAmmunition(supplyDto.getAmmunition());
        scheduleReportDto.setEquipment(supplyDto.getEquipment());
        return scheduleReportDto;
    }

    @Override
    public RegimentDto extractRegiment(ScheduleReportDto scheduleReportDto){
        RegimentDto regimentDto = new RegimentDto();
        regimentDto.setCode(scheduleReportDto.getCode());
        regimentDto.setStrength(scheduleReportDto.getStrength());
        regimentDto.setStamina(scheduleReportDto.getStamina());
        regimentDto.setShooting(scheduleReportDto.getShooting());
        regimentDto.setIntelligence(scheduleReportDto.getIntelligence());
        regimentDto.setMedSkills(scheduleReportDto.getMedSkills());
        regimentDto.setTypeName(scheduleReportDto.getTypeName());
        return  regimentDto;
    }
    @Override
    public SupplyDto extractSupply(ScheduleReportDto scheduleReportDto){

        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setEquipment(scheduleReportDto.getEquipment());
        supplyDto.setAmmunition(scheduleReportDto.getAmmunition());
        supplyDto.setFood(scheduleReportDto.getFood());
        return  supplyDto;
    }
}
