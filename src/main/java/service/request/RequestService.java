package service.request;

import dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(RequestDto requestDto);

    List<RequestDto> showAll();

    RequestDto findByLocation(String location);

    RequestDto updateRequest(RequestDto requestDto);

    RequestDto addUnit(RequestDto requestDto, String typeName);

    void removeAll();

    void removeRequest(int id);

}
