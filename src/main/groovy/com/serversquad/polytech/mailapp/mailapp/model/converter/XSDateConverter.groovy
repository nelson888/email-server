package com.serversquad.polytech.mailapp.mailapp.model.converter

import org.springframework.lang.Nullable

import java.text.SimpleDateFormat

/**
 * NOT THREAD SAFE
 */
class XSDateConverter {

    private static final ThreadLocalDateFormat THREAD_LOCAL_DATE_FORMAT = new ThreadLocalDateFormat()

  static Date parse(@Nullable Object o) throws Exception {
        if (o == null) {
            return null
        }
        return THREAD_LOCAL_DATE_FORMAT.get().parse(o.toString())
  }

  static String format(@Nullable Date date) throws Exception {
        if (date == null) {
            return null
        }
        return THREAD_LOCAL_DATE_FORMAT.get().format(date)
  }

    private static class ThreadLocalDateFormat extends ThreadLocal<SimpleDateFormat> {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        }
    }
}
