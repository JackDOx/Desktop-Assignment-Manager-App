package model;

import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventLogPrinter {
    private static final int TIME_DIFFERENCE_SECONDS = 5;

    public static void printEventLog(EventLog eventLog) {
        Iterator<Event> iterator = eventLog.iterator();

        if (!iterator.hasNext()) {
            return; // No events to print
        }

        Event firstEvent = iterator.next();
        String loadDateStr = formatDate(firstEvent.getDate());

        int loopNum = 0;
        while (iterator.hasNext()) {
            Event event = iterator.next();
            // Skip the first Event as first Event is the load time
            if (loopNum == 0) {
                loopNum++;
                continue;
            }

            String eventDateStr = formatDate(event.getDate());

            if (!isWithinTimeRange(event.getDate(), firstEvent.getDate(), TIME_DIFFERENCE_SECONDS)) {
                System.out.println("Date: " + event.getDate() + "\nDescription: " + event.getDescription() + "\n");
            }
        }
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private static boolean isWithinTimeRange(Date date1, Date date2, int seconds) {
        long timeDifference = Math.abs(date1.getTime() - date2.getTime()) / 1000; // Convert to seconds
        return timeDifference < seconds;
    }
}