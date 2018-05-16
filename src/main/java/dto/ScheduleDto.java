package dto;

import entity.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDto {

    private int id;
    private Date date;
    private int regimentId;
    private int regimentCode;
    private ScheduleReport scheduleReport;
    private List<ActivityDto> activities;




    public ScheduleReport getScheduleReport() {
        return scheduleReport;
    }

    public void setScheduleReport(ScheduleReport scheduleReport) {
        this.scheduleReport = scheduleReport;
    }

    public ScheduleDto() {
        this.scheduleReport = new ScheduleReport();
        this.activities = new ArrayList<ActivityDto>();
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
