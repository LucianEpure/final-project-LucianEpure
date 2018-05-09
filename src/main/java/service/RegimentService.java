package service;

import dto.UserDto;
import entity.Regiment;
import validators.Notification;

import java.util.List;

public interface RegimentService {

    void registerAdmin(UserDto userDto);

    Notification<Boolean> enlistRegiment(int code, String password);
   // Notification<Boolean> enlistRegiment(RegimentDto regiment);
    void removeRegiment(int id);

    List<Regiment> showAll();
}
