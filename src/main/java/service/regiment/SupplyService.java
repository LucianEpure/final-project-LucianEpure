package service.regiment;

import dto.SupplyDto;

public interface SupplyService {

    void addMoreSupplies(SupplyDto supplyDto, int regimentCode);

    SupplyDto findSupplies(int supplyId);


}
