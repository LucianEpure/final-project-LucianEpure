package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Regiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int stamina;
    private int strength;
    private int shooting;
    private int intelligence;
    private int medSkills;
    private int code;


    @OneToOne
    @JoinColumn(name = "supply_id")
    private  Supply supply;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private  Schedule schedule;

    @OneToOne
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    public Regiment(){
        this.intelligence = 10;
        this.stamina = 20;
        this.strength = 10;
        this.shooting = 0;
        this.medSkills = 0;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public int getMedSkills() {
        return medSkills;
    }

    public void setMedSkills(int medSkills) {
        this.medSkills = medSkills;
    }


    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
