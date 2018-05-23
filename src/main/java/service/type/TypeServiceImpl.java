package service.type;

import converter.TypeConverter;
import dto.TypeDto;
import entity.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TypeRepository;

@Service
public class TypeServiceImpl implements TypeService{

    TypeRepository typeRepository;
    TypeConverter typeConverter;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository, TypeConverter typeConverter){
        this.typeRepository = typeRepository;
        this.typeConverter = typeConverter;
    }

    @Override
    public TypeDto findTypeByName(String typeName) {
        Type type = typeRepository.findByTypeName(typeName);
        TypeDto typeDto = typeConverter.convertToDto(type);
        return typeDto;
    }
}
