package service;

import java.util.Date;

public interface DateTime {

    Date formatter(String toFormatDate);

    Date noTime(Date date);
}
