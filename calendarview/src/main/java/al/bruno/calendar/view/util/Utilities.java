package al.bruno.calendar.view.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Utilities {
    public static String month(DateTime dateTime) {
        return DateTimeFormat.forPattern("MMMM").print(dateTime);
    }
}
