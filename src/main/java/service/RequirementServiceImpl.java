package service;

import converter.RequirementConverter;
import dto.RequirementDto;
import entity.Regiment;
import entity.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RegimentRepository;
import repository.RequirementRepository;

@Service
public class RequirementServiceImpl implements RequirementService {


    private RequirementRepository requirementRepository;
    private RequirementConverter requirementConverter;
    private RegimentRepository regimentRepository;

    @Autowired
    public RequirementServiceImpl(RequirementRepository requirementRepository, RequirementConverter requirementConverter, RegimentRepository regimentRepository){
        this.requirementRepository = requirementRepository;
        this.requirementConverter = requirementConverter;
        this.regimentRepository = regimentRepository;
    }
    @Override
    public RequirementDto findRequirement(int requirementId) {
        Requirement requirement =requirementRepository.getOne(requirementId);
        return requirementConverter.convertRequirementToDto(requirement);
    }

    @Override
    public void changeRequirements(RequirementDto requirementDto, int regimentCode) {
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Requirement requirement = regiment.getRequirement();
        requirement.setRequiredIntelligence(requirementDto.getRequiredIntelligence());
        requirement.setRequiredMedSkills(requirementDto.getRequiredMedSkills());
        requirement.setRequiredShooting(requirementDto.getRequiredShooting());
        requirement.setRequiredStamina(requirementDto.getRequiredStamina());
        requirement.setRequiredStrength(requirementDto.getRequiredStrength());
        requirementRepository.save(requirement);
    }
}
