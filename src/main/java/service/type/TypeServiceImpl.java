package service.type;

import converter.type.TypeConverter;
import dto.TypeDto;
import entity.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TypeRepository;

@Service
public class TypeServiceImpl implements TypeService{

    TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository){
        this.typeRepository = typeRepository;

    }

    @Override
    public Type addNewType(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        return typeRepository.save(type);
    }
}
