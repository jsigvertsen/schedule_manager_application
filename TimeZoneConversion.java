package sigvertsen.c195.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** class TimeZoneConversion.java*/
public class TimeZoneConversion {

    /** This is the ConvertToZone method for the TimeZoneConversion class
     * This method will take in a time and return the time in the requested time zone
     * */
    public static LocalDateTime ConvertToZone(LocalDateTime time, ZoneId from_zone, ZoneId to_zone) {

        ZonedDateTime start_zone = time.atZone(from_zone);
        ZonedDateTime start_time_dest_zone = start_zone.withZoneSameInstant(to_zone);

        return start_time_dest_zone.toLocalDateTime();
    }
}


