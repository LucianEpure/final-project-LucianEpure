package dto;

import service.notify.Message;

import java.util.*;

import static application.Constants.QUARTERMASTER;
import static application.Constants.REGIMENTCOMMANDER;
import static application.Constants.WAITING_APPROVAL;

public class ScheduleDto{

    private int id;
    private Date date;
    private int regimentId;
    private int regimentCode;
    private ScheduleReportDto scheduleReport;
    private List<ActivityDto> activities;
    private String activityNames;
    private String approved;

    //private List<UserDto> observers;




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
    /*public void addObserver(UserDto observer){
        observers.add(observer);
    }
    public void notifyAdmin(Message message){
        for(UserDto observer:observers){
            if(observer.getRoles().equalsIgnoreCase(QUARTERMASTER))
                observer.update(message);
        }
    }
    public void notifyRegCommanders(Message message){
        for(UserDto observer:observers){
            if(observer.getRoles().equalsIgnoreCase(REGIMENTCOMMANDER))
                observer.update(message);
        }
    }*/

    public void notifyObs(){

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
