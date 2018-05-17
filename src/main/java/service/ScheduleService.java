package service;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import entity.Schedule;
import validators.Notification;

import java.util.Date;
import java.util.List;

public interface ScheduleService {


    ScheduleDto save (ScheduleDto scheduleDto);

    ActivityDto findActivityByName(String activityName);


    List<ScheduleDto> findByDate(Date date);

    boolean checkIfApproved(ScheduleDto scheduleDto);

   ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activityDto);

    ScheduleDto removeActivity(ScheduleDto scheduleDto);

   Notification<Boolean> update(ScheduleDto scheduleDto);

   List<ScheduleDto> showAll();

   ScheduleDto findById(int id);

}
