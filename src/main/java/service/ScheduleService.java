package service;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;

public interface ScheduleService {


    ScheduleDto save (ScheduleDto scheduleDto, int regimentCode);

    ActivityDto findActivityByName(String activityName);

   ScheduleDto addActivity(ScheduleDto scheduleDto, ActivityDto activityDto);

    ScheduleDto removeActivity(ScheduleDto scheduleDto);

   ScheduleDto update(ScheduleDto scheduleDto);

}
