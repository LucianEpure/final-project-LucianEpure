package service.request;

import converter.RequestConverter;
import dto.RequestDto;
import entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {

    RequestRepository requestRepository;
    RequestConverter requestConverter;

    @Autowired
    RequestServiceImpl(RequestRepository requestRepository, RequestConverter requestConverter){
        this.requestRepository = requestRepository;
        this.requestConverter = requestConverter;
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
    public void removeRequest(int id) {
        requestRepository.deleteById(id);
    }

}
