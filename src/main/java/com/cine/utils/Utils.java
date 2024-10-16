package com.cine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  public static Date overcomeToDate(String dateString) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = formatter.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException("No se pudo convertir la  fecha: " + e.getMessage());
    }
    return date;
  }
}
