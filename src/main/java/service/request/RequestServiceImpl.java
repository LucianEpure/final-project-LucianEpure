package service.request;

import converter.request.RequestConverter;
import converter.type.TypeConverter;
import dto.RequestDto;
import dto.TypeDto;
import entity.Request;
import entity.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RequestRepository;
import repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    RequestRepository requestRepository;
    RequestConverter requestConverter;
    TypeRepository typeRepository;
    TypeConverter typeConverter;

    @Autowired
    RequestServiceImpl(TypeConverter typeConverter, TypeRepository typeRepository, RequestRepository requestRepository, RequestConverter requestConverter){
        this.requestRepository = requestRepository;
        this.requestConverter = requestConverter;
        this.typeConverter = typeConverter;
        this.typeRepository = typeRepository;
    }

    @Override
    public RequestDto addRequest(RequestDto requestDto) {
        Request request = requestConverter.convertRequestFromDto(requestDto);
        return requestConverter.convertRequestToDto(requestRepository.save(request));

    }

    @Override
    public List<RequestDto> showAll() {
        List<RequestDto> requestDtos = new ArrayList<RequestDto>();
        List<Request> requests= requestRepository.findAll();
        for(Request request:requests){
            requestDtos.add(requestConverter.convertRequestToDto(request));

        }
        return  requestDtos;
    }

    @Override
    public RequestDto findByLocation(String location) {
        Request request = requestRepository.findByLocationName(location);
        return requestConverter.convertRequestToDto(request);
    }

    @Override
    public RequestDto updateRequest(RequestDto requestDto) {
        Request request = requestConverter.convertRequestFromDto(requestDto);
        if(!request.getTypes().isEmpty())
            requestRepository.save(request);
        else
            this.removeRequest(requestDto.getId());
        return requestDto;
    }

    @Override
    public RequestDto addUnit(RequestDto requestDto, String typeName) {
        List<TypeDto> typeDtos = requestDto.getTypes();
        Type type = typeRepository.findByTypeName(typeName);
        TypeDto typeDto = typeConverter.convertToDto(type);
        typeDtos.add(typeDto);
        requestDto.setTypes(typeDtos);
        return requestDto;
    }

    @Override
    public void removeAll() {
        requestRepository.deleteAll();
    }

    @Override
    public void removeRequest(int id) {
        requestRepository.deleteById(id);
    }

}
