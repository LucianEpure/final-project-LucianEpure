package converter;

import dto.SupplyDto;
import entity.Supply;

public interface SupplyConverter {

    SupplyDto convertSupplyToDto(Supply supply);
}
