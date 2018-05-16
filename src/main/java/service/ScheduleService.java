package service;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import validators.Notification;

public interface ScheduleService {


    ScheduleDto save (ScheduleDto scheduleDto);

    ActivityDto findActivityByName(String activityName);

   ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activityDto);

    ScheduleDto removeActivity(ScheduleDto scheduleDto);

   Notification<Boolean> update(ScheduleDto scheduleDto);

}
