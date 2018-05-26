package converter.regiment;

import dto.RequirementDto;
import entity.Requirement;
import org.springframework.stereotype.Component;

@Component
public interface RequirementConverter {

    RequirementDto convertRequirementToDto(Requirement requirement);
}
