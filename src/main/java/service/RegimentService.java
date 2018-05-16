package service;

import dto.RegimentDto;
import dto.RequirementDto;
import dto.SupplyDto;
import dto.UserDto;
import entity.Regiment;
import entity.Supply;
import entity.Type;
import validators.Notification;

import java.util.List;

public interface RegimentService {

    void registerAdmin(UserDto userDto);

    Notification<Boolean> enlistRegiment(int code, String password);
   // Notification<Boolean> enlistRegiment(RegimentDto regiment);
    void removeRegiment(int code);

    RegimentDto findByCode(int code);

    RegimentDto update(RegimentDto regimentDto, SupplyDto supplyDto);

    Type addNewType(String typeName);

    List<RegimentDto> showAll();
}
