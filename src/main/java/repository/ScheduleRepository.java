package repository;

import entity.Regiment;
import entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {


        List<Schedule> findByRegimentAndDate(Regiment regiment, Date date);

        List<Schedule> findByDate(Date date);

        void deleteByRegiment(Regiment regiment);
}
