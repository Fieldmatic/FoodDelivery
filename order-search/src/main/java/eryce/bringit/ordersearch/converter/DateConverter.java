package eryce.bringit.ordersearch.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
    public static Date toDate(LocalDateTime date) {
        return java.util.Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
