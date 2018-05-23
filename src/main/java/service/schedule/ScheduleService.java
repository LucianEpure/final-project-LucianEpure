package service.schedule;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import entity.Schedule;
import validators.Notification;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    ActivityDto findActivityByName(String activityName);

    List<ScheduleDto> findByDate(Date date);

    void approveSchedule(int scheduleId);

    void denySchedule(int scheduleId);

    boolean checkIfApproved(ScheduleDto scheduleDto);

   ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activityDto);

   ScheduleDto removeActivity(ScheduleDto scheduleDto);



}
