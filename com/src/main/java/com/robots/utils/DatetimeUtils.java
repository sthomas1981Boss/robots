package com.robots.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatetimeUtils {

    public static Date toDate(long datetime) {
        return Date.from(Instant.ofEpochMilli(datetime).atZone(ZoneOffset.UTC).toInstant());
    }

    public static long toLong(LocalDateTime datetime) {
        return datetime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static LocalDateTime toDatetime(String datetime) {
        Long dt = Long.parseLong(datetime);
        return Instant.ofEpochMilli(dt)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static LocalDateTime toDateTimeFromString(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
        return dateTime;
    }

    public static LocalDateTime toDatetime(long datetime) {
        return Instant.ofEpochMilli(datetime)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static String toString(long datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = Instant.ofEpochMilli(datetime)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        return dateTime.format(formatter);
    }

    public static ZonedDateTime toZoneDt(LocalDateTime dt) {
        return dt.atZone(ZoneId.of("Europe/Zurich"));
    }

    public static ZonedDateTime toZoneDt(String dt) {
        return toDatetime(dt).atZone(ZoneId.of("Europe/Zurich"));
    }

}
