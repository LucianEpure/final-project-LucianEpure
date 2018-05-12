package entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    public Schedule() {
        this.activities = new ArrayList<Activity>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Regiment getRegiment() {
        return regiment;
    }

    public void setRegiment(Regiment regiment) {
        this.regiment = regiment;
    }
}
