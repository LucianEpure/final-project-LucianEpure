package service.regiment;

import dto.*;
import entity.Regiment;
import entity.Supply;
import entity.Type;
import validators.Notification;

import java.util.List;

public interface RegimentService {



    Notification<Boolean> enlistRegiment(int code, String password);

    Notification<Boolean> sendRegimentToWar(int regimentCode, RequestDto requestDto);

    RegimentDto findByCode(int code);

    RegimentDto update(RegimentDto regimentDto, SupplyDto supplyDto);

    int extractRegimentFromUser(String username);

    void removeAll();

    List<RegimentDto> showAll();
}
