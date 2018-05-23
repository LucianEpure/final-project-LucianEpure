package converter;

import dto.TypeDto;
import entity.Type;
import org.springframework.stereotype.Component;

@Component
public class TypeConverterImpl implements TypeConverter {
    @Override
    public TypeDto convertToDto(Type type) {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(type.getId());
        typeDto.setTypeName(type.getTypeName());
        return  typeDto;
    }

    @Override
    public Type convertFromDto(TypeDto typeDto) {
        Type type = new Type();
        type.setId(typeDto.getId());
        type.setTypeName(typeDto.getTypeName());
        return type;
    }
}
