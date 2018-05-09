package service;

import dto.UserDto;
import validators.Notification;

public interface UserService {

    Notification<Boolean> register(UserDto user, String type);

   // Notification<Boolean> enlistRegiment(RegimentDto regiment);
}
