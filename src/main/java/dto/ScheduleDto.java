package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static application.Constants.WAITING_APPROVAL;

public class ScheduleDto {

    private int id;
    private Date date;
    private int regimentId;
    private int regimentCode;
    private ScheduleReportDto scheduleReport;
    private List<ActivityDto> activities;
    private String activityNames;
    private String approved;




    public ScheduleReportDto getScheduleReport() {
        return scheduleReport;
    }

    public void setScheduleReport(ScheduleReportDto scheduleReport) {
        this.scheduleReport = scheduleReport;
    }

    public ScheduleDto() {
        this.scheduleReport = new ScheduleReportDto();
        this.activities = new ArrayList<ActivityDto>();
        this.approved = WAITING_APPROVAL;
    }


    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getActivityNames() {
        return activityNames;
    }

    public void setActivityNames(String activityNames) {
        this.activityNames = activityNames;
    }

    public int getRegimentCode() {
        return regimentCode;
    }

    public void setRegimentCode(int regimentCode) {
        this.regimentCode = regimentCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ActivityDto> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDto> activities) {
        this.activities = activities;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRegimentId() {
        return regimentId;
    }

    public void setRegimentId(int regimentId) {
        this.regimentId = regimentId;
    }
}
