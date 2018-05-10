package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;

    @ManyToMany()
    @JoinTable(name = "schedule_activities",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"))
    private List<Activity> activities;

    @ManyToOne()
    @JoinColumn(name = "regiment_id")
    private Regiment regiment;



}
