package service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateTimeImpl implements DateTime{


    @Override
    public Date formatter(String toFormatDate) {
        Date parsedDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parsedDate = formatter.parse(toFormatDate);
           // session.setAttribute("scheduleDate",parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    @Override
    public Date noTime(Date date) {
        Date todayWithZeroTime = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            todayWithZeroTime = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  todayWithZeroTime;
    }
}
