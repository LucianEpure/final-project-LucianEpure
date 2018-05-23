package service.request;

import dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(RequestDto requestDto);

    List<RequestDto> showAll();

    void removeRequest(int id);

}
