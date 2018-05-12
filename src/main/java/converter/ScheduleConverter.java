package converter;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import entity.Schedule;

public interface ScheduleConverter {

    public ScheduleDto convertScheduleToDto(Schedule schedule);

    public ActivityDto convertActivityToDto(Activity activity);

    public Activity convertActivityFromDto(ActivityDto activityDto);
}
