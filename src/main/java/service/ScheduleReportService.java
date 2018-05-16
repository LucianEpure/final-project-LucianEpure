package service;

import dto.ActivityDto;
import dto.RegimentDto;
import dto.ScheduleReport;
import dto.SupplyDto;

public interface ScheduleReportService {

    ScheduleReport updateStats(ScheduleReport scheduleReport, ActivityDto activity, String type);

    RegimentDto retrieveRegiment(ScheduleReport scheduleReport);

    SupplyDto retrieveSupply(ScheduleReport scheduleReport);
}
