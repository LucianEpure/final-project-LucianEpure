package service.schedule;

import dto.ScheduleDto;
import validators.Notification;

import java.util.List;

public interface ScheduleCRUDService {

    ScheduleDto save (ScheduleDto scheduleDto);

    Notification<Boolean> update(ScheduleDto scheduleDto);

    List<ScheduleDto> showAll();

    void removeSchedulesOfRegiment(int regimentCode);

    ScheduleDto findById(int id);
}
