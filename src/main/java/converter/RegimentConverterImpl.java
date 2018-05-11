package converter;

import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
import dto.UserDto;
import entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegimentConverterImpl implements RegimentConverter {

    @Override
    public UserDto convertUserToDto(User user){
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        List<Role> roles = user.getRoles();
        for(Role role:roles)
            userDto.setRoles(userDto.getRoles()+" "+role.getRoleName());
        return userDto;
    }

    @Override
    public RegimentDto convertRegimentToDto(Regiment regiment) {
        RegimentDto regimentDto = new RegimentDto();
        regimentDto.setId(regiment.getId());
        regimentDto.setCode(regiment.getCode());
        regimentDto.setIntelligence(regiment.getIntelligence());
        regimentDto.setStamina(regiment.getStamina());
        regimentDto.setStrength(regiment.getStrength());
        regimentDto.setStrength(regiment.getShooting());
        regimentDto.setSupplyId(regiment.getSupply().getId());
        regimentDto.setTypeId(regiment.getType().getId());
        regimentDto.setRequirementId(regiment.getRequirement().getId());
        regimentDto.setMedSkills(regiment.getMedSkills());
        regimentDto.setTypeName(regiment.getType().getTypeName());

        return regimentDto;
    }

    @Override
    public SupplyDto convertSupplyToDto(Supply supply) {
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(supply.getId());
        supplyDto.setFood(supply.getFood());
        supplyDto.setAmmunition(supply.getAmmunition());
        supplyDto.setEquipment(supply.getEquipment());
        return  supplyDto;
    }

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
