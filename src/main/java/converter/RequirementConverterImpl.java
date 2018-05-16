package converter;

import dto.RequirementDto;
import entity.Requirement;
import org.springframework.stereotype.Component;

@Component
public class RequirementConverterImpl implements RequirementConverter {

    @Override
    public RequirementDto convertRequirementToDto(Requirement requirement) {
        RequirementDto requirementDto = new RequirementDto();
        requirementDto.setId(requirement.getId());
        requirementDto.setRequiredIntelligence(requirement.getRequiredIntelligence());
        requirementDto.setRequiredMedSkills(requirement.getRequiredMedSkills());
        requirementDto.setRequiredShooting(requirement.getRequiredShooting());
        requirementDto.setRequiredStamina(requirement.getRequiredStamina());
        requirementDto.setRequiredStrength(requirement.getRequiredStrength());
        return requirementDto;
    }
}
