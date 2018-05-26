package converter.schedule;

import dto.RegimentDto;
import dto.ScheduleReportDto;
import dto.SupplyDto;
import entity.Regiment;
import repository.ScheduleRepository;

public interface ScheduleReportConverter {

    ScheduleReportDto convertToScheduleReport(RegimentDto regimentDto, SupplyDto supplyDto);

    RegimentDto extractRegiment(ScheduleReportDto scheduleReportDto);

    SupplyDto extractSupply(ScheduleReportDto scheduleReportDto);
}
