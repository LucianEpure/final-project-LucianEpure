package converter.regiment;

import dto.SupplyDto;
import entity.Supply;
import org.springframework.stereotype.Component;


@Component
public class SupplyConverterImpl implements SupplyConverter {
    @Override
    public SupplyDto convertSupplyToDto(Supply supply) {
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(supply.getId());
        supplyDto.setFood(supply.getFood());
        supplyDto.setAmmunition(supply.getAmmunition());
        supplyDto.setEquipment(supply.getEquipment());
        return  supplyDto;
    }
}
