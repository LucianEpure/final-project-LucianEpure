package service.regiment;

import converter.regiment.SupplyConverter;
import dto.SupplyDto;
import entity.Regiment;
import entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RegimentRepository;
import repository.SupplyRepository;

@Service
public class SupplyServiceImpl implements SupplyService {

    private RegimentRepository regimentRepository;
    private SupplyRepository supplyRepository;
    private SupplyConverter supplyConverter;

    @Autowired
    public SupplyServiceImpl(RegimentRepository regimentRepository, SupplyRepository supplyRepository, SupplyConverter supplyConverter) {
        this.regimentRepository = regimentRepository;
        this.supplyRepository = supplyRepository;
        this.supplyConverter = supplyConverter;
    }

    @Override
    public void addMoreSupplies(SupplyDto supplyDto, int regimentCode) {
        Regiment regiment = regimentRepository.findByCode(regimentCode);
        Supply supply = regiment.getSupply();
        supply.setAmmunition(supply.getAmmunition() + supplyDto.getAmmunition());
        supply.setEquipment(supply.getEquipment() + supplyDto.getEquipment());
        supply.setFood(supply.getFood() + supplyDto.getFood());
        supplyRepository.save(supply);
    }

    @Override
    public SupplyDto findSupplies(int supplyId) {
        Supply supply = supplyRepository.getOne(supplyId);
        return supplyConverter.convertSupplyToDto(supply);
    }
}
