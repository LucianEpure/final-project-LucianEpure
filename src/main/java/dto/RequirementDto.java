package dto;

public class RequirementDto {

    private int id;
    private int requiredStamina;
    private int requiredStrength;
    private int requiredIntelligence;
    private int requiredShooting;
    private int requiredMedSkills;

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
