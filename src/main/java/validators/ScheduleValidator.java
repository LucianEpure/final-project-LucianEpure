package validators;

import dto.ScheduleDto;

import java.util.ArrayList;
import java.util.List;

public class ScheduleValidator implements IValidator {


    private final ScheduleDto scheduleDto;
    private final List<String> errors;


    public ScheduleValidator(ScheduleDto scheduleDto){
        this.scheduleDto = scheduleDto;
        errors = new ArrayList<String>();
    }
    @Override
    public boolean validate() {
        validateDuration(scheduleDto.getScheduleReport().getDuration());
        validateStamina(scheduleDto.getScheduleReport().getStamina());
        return errors.isEmpty();
    }
    private void validateDuration(int duration) {
        if (duration>24) {
            errors.add("Duration can not be longer than 24 hours!");
        }
    }

    private void validateStamina(int stamina){
        if(stamina<0){
            errors.add("Stamina can not drop below 0");
        }
    }


    @Override
    public List<String> getErrors() {
        return errors;
    }
}
