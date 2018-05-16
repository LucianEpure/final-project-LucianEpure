package service;

import dto.RequirementDto;
import org.springframework.stereotype.Service;

@Service
public interface RequirementService {

    RequirementDto findRequirement(int requirementId);

    void changeRequirements(RequirementDto requirementDto, int regimentCode);
}
