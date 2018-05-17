package converter;

import dto.ActivityDto;
import dto.ScheduleDto;
import entity.Activity;
import entity.Schedule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduleConverterImpl implements ScheduleConverter{

    RegimentConverter regimentConverter;
    ActivityConverter activityConverter;

    public ScheduleConverterImpl(RegimentConverter regimentConverter, ActivityConverter activityConverter){
        this.regimentConverter = regimentConverter;
        this.activityConverter = activityConverter;
    }
    @Override
    public ScheduleDto convertScheduleToDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDate(schedule.getDate());
        scheduleDto.setRegimentId(schedule.getRegiment().getId());
        scheduleDto.setRegimentCode(schedule.getRegiment().getCode());
        List<ActivityDto> activities = new ArrayList<ActivityDto>();
        String activitiesNames = " ";
        for(Activity activity:schedule.getActivities())
        {
           ActivityDto activityDto = (activityConverter.convertActivityToDto(activity));
           activitiesNames = activitiesNames+" "+ activityDto.getActivityName();
           activities.add(activityDto);
        }
        scheduleDto.setActivities(activities);
        scheduleDto.setActivityNames(activitiesNames);
        scheduleDto.setApproved(schedule.getApproved());
        //scheduleDto.setRegimentStats(regimentConverter.convertRegimentToDto(schedule.getRegiment()));
        //System.out.println("REGIMEEEEEEEEEENT"+ scheduleDto.getRegimentStats().getTypeName());

        return scheduleDto;
    }


}
