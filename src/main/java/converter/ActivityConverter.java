package converter;

import dto.ActivityDto;
import entity.Activity;

public interface ActivityConverter {

    public ActivityDto convertActivityToDto(Activity activity);

    public Activity convertActivityFromDto(ActivityDto activityDto);
}
