package converter;

import dto.RequestDto;
import dto.TypeDto;
import entity.Request;
import entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RequestConverterImpl implements RequestConverter {

    TypeConverter typeConverter;

    @Autowired
    public RequestConverterImpl(TypeConverter typeConverter){
        this.typeConverter = typeConverter;
    }

    @Override
    public RequestDto convertRequestToDto(Request request) {
        int count = 0;
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setLocationName(request.getLocationName());
        List<TypeDto> types = new ArrayList<TypeDto>();
        for(Type type:request.getTypes())
            types.add(typeConverter.convertToDto(type));
        requestDto.setTypes(types);
        Map<String,Integer> totalTypes = requestDto.getTotalTypes();
        count =0;
        for(TypeDto type:requestDto.getTypes()){
            if(totalTypes.get(type.getTypeName())==null)
                 totalTypes.put(type.getTypeName(),1);
            else{
                count = totalTypes.get(type.getTypeName());
                totalTypes.put(type.getTypeName(),count+1);
            }

        }
        requestDto.setTotalTypes(totalTypes);
        requestDto.totalTypesToString();
        return requestDto;
    }

    @Override
    public Request convertRequestFromDto(RequestDto requestDto) {
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setLocationName(requestDto.getLocationName());
        List<Type> types = new ArrayList<Type>();
        for(TypeDto typeDto:requestDto.getTypes()){
            types.add(typeConverter.convertFromDto(typeDto));
        }
        request.setTypes(types);
        return request;
    }
}
