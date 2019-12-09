package com.serversquad.polytech.mailapp.mailapp.model.converter

import java.text.SimpleDateFormat;

/**
 * NOT THREAD SAFE
 */
public class JSDateConverter {

  private static final ThreadLocalDateFormat THREAD_LOCAL_DATE_FORMAT = new ThreadLocalDateFormat()

  public static Date parse(Object o) throws Exception {
//    return THREAD_LOCAL_DATE_FORMAT.get().parse(o.toString());
    return new Date()
  }

  public static String format(Date date) throws Exception {
    return THREAD_LOCAL_DATE_FORMAT.get().format(date);
  }

  private static class ThreadLocalDateFormat extends ThreadLocal<SimpleDateFormat> {

    @Override
    protected SimpleDateFormat initialValue() {
      return new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH)
    }
  }
}
