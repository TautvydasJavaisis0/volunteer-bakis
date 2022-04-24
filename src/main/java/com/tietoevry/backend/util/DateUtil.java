package com.tietoevry.backend.util;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

    private static final String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_FORMAT_O = "yyyy-MM-dd' 'HH:mm";
    private static SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
    private static SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public static Date StringToDate(String date){
        Date newDate = new Date();
        try {
            newDate = formatInput.parse(date);
        } catch (Throwable t) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blogas datos formatas");
        }
        return newDate;
    }

    public static String DateToString(Date date){
        return formatOutput.format(date);
    }

}
