package converter;

import dto.TypeDto;
import entity.Type;

public interface TypeConverter {

    TypeDto convertToDto(Type type);

    Type convertFromDto(TypeDto typeDto);
}
