package converter;

import dto.RequestDto;
import entity.Request;

public interface RequestConverter {

    RequestDto convertRequestToDto(Request request);

    Request convertRequestFromDto(RequestDto requestDto);
}
