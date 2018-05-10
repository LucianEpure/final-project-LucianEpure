package entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int requiredStamina;
    private int requiredStrength;
    private int requiredIntelligence;
    private int requiredShooting;
    private int requiredMedSkills;

    public Requirement() {
        this.requiredStamina = 20;
        this.requiredStrength = 20;
        this.requiredIntelligence = 20;
        this.requiredShooting = 10;
        this.requiredMedSkills = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequiredStamina() {
        return requiredStamina;
    }

    public void setRequiredStamina(int requiredStamina) {
        this.requiredStamina = requiredStamina;
    }

    public int getRequiredStrength() {
        return requiredStrength;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }

    public int getRequiredIntelligence() {
        return requiredIntelligence;
    }

    public void setRequiredIntelligence(int requiredIntelligence) {
        this.requiredIntelligence = requiredIntelligence;
    }

    public int getRequiredShooting() {
        return requiredShooting;
    }

    public void setRequiredShooting(int requiredShooting) {
        this.requiredShooting = requiredShooting;
    }
    public int getRequiredMedSkills() {
        return requiredMedSkills;
    }

    public void setRequiredMedSkills(int requiredMedSkills) {
        this.requiredMedSkills = requiredMedSkills;
    }
}
