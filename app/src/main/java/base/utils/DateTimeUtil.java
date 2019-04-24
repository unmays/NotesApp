package base.utils;

import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static String INPUT_DATE_DD_MM_YY_TS = "dd/MM/yyyy HH:mm:ss";
    public static String INPUT_DATE_YYYY_MM_DD_TS = "yyyy/MM/dd HH:mm:ss";
    public static String INPUT_DATE_YYYY_MM_DD_TS_HYPHON = "yyyy-MM-dd HH:mm:ss";
    public static String INPUT_DATE_YYYY_MM_DD_T_TS = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static String INPUT_DATE_YY_MM = "yyyy/MM/dd";
    public static String INPUT_DATE_YY_MM_HYPHON = "yyyy-MM-dd";
    public static String INPUT_DATE_DD_MM = "dd/MM/yyyy";
    public static String OUTPUT_DATE_DD_MM = "dd/MM/yyyy";
    public static String OUTPUT_DATE_MMM_YYYY = "MMMM, yyyy";
    public static String OUTPUT_DATE_DD_MMM_YYYY = "dd MMM yyyy";
    public static String OUTPUT_DATE_DD_MMM_YYYY_HYPHON = "dd-MMM-yyyy";
    public static String OUTPUT_DATE_DD_MMM_YYYY_TRANSACTION = "dd-MMM-yyyy & h:mm a";
    public static String OUTPUT_DATE_MMYYYY = "MMyyyy";
    public static String OUTPUT_DATE_YYYYMM = "yyyyMM";
    public static String OUTPUT_DATE_YYYYMMdd = "yyyyMMdd";
    public static String OUTPUT_DATE_YYYY = "yyyy";
    public static String OUTPUT_DATE_dd = "dd";
    public static String OUTPUT_DATE_ddMM = "ddMM";
    public static String OUTPUT_DATE_ddMMM = "ddMMM";
    public static String OUTPUT_DATE_dd_MMM = "dd MMM";
    public static String OUTPUT_DATE_MMM = "MMM";
    public static String OUTPUT_DATE_MMM_YY = "MMM yy";
    public static String OUTPUT_DATE_MMM_YY_APOSTROPHY = "MMM yy";
    public static String OUTPUT_DATE_MM = "MM";
    public static String OUTPUT_DATE_YYYY_MM = "yyyy/MM";
    public static String OUTPUT_DATE_dd_MMM_yy = "dd MMM yy";
    public static String OUTPUT_HYPHEN_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static String OUTPUT_HYPHEN_DATE_DD_MM_YYYY = "dd-MM-yyyy";
    public static String TIME_UNIT_HOUR = "hours";
    public static String TIME_UNIT_MINUTES = "minutes";
    public static String TIME_UNIT_SECONDS = "seconds";
    public static String TIME_UNIT_MILLI_SECOND = "seconds";
    public static String SORT_MONTH_AND_FULL_YEAR = "MMM, yyyy";
    public static String SORT_DATE_AND_FULL_YEAR = "MMM dd, yyyy";
    public static String MMM_YY_APOSTROPHE = "MMM ''yy";
    public static String DD_MMM_YY_APOSTROPHE = "dd MMM ''yy";
    public static final String FORMAT_SERVER_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_SERVER_DATE = "yyyy-MM-dd";
    public static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    public static String OUTPUT_DATE_TIME_LAST_LOGIN = "dd-MMM-yyyy h:mm a";

    public static String changeDateFormat(String date, String inputDateFormet,
                                          String outputDateFormat) {
        try {
            String formatedDate = "";

            if (date != null && !date.isEmpty() && !date.equalsIgnoreCase("None")) {
                SimpleDateFormat format1 = new SimpleDateFormat(inputDateFormet, Locale.getDefault());
                SimpleDateFormat format2 = new SimpleDateFormat(outputDateFormat, Locale.getDefault());
                Date inDate = null;
                try {
                    inDate = format1.parse(date);
                    formatedDate = format2.format(inDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    formatedDate = date;
                }

            }
            return formatedDate;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrentDate(String dateFormatStr) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return (dateFormat.format(cal.getTime()));
    }

    public static long convertDateInMiliSec(String date, String inputDateFormet) {
        long dateInMiliSec;
        try {
            Date dateFormat = new SimpleDateFormat(inputDateFormet, Locale.getDefault()).parse(date);
            dateInMiliSec = dateFormat.getTime();
        } catch (ParseException e) {
            dateInMiliSec = 0l;
            e.printStackTrace();
        }
        return dateInMiliSec;
    }

    public static int getDaysOfCurrentMonth() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static long getDaysBetweenDate(String startDate, String endDate, String inputFormat) {
        try {
            return (convertDateInMiliSec(endDate, inputFormat) - convertDateInMiliSec(startDate,
                    inputFormat))
                    / (24 * 60 * 60 * 1000);
        } catch (Exception exception) {
            return 0L;
        }
    }

    public static String getLastDateOfMonth(int differenceFromCurrentMonth) {
        try {

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, differenceFromCurrentMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            DateFormat sdf = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
            return sdf.format(calendar.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String getStartDateOfMonth(int differenceFromCurrentMonth) {
        try {
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, differenceFromCurrentMonth);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            DateFormat sdf = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
            return sdf.format(calendar.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String getMonthName(int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthNum);
        //format it to MMM-yyyy // January-2012
        String previousMonthYear = new SimpleDateFormat("MMM-yyyy", Locale.getDefault()).format(cal.getTime());
        if (previousMonthYear != null) {
            return previousMonthYear;
        } else {
            return "";
        }
    }

    public static Date parseServerDateTime(String serverDate) throws ParseException {
        return parseDate(serverDate, FORMAT_SERVER_DATE_TIME);
    }

    public static Date parseServerDate(String serverDate) throws ParseException {
        return parseDate(serverDate, FORMAT_SERVER_DATE);
    }

    public static Date parseDate(String serverDate, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.parse(serverDate);
    }

    public static int dateDifferenceInDays(Date date1, Date date2) {
        long date1Millis = date1.getTime();
        long date2Millis = date2.getTime();
        return (int) ((date1Millis - date2Millis) / DAY_IN_MILLIS);
    }

    public static String convertServerDateToClient(String date) {
        return changeDateFormat(date, FORMAT_SERVER_DATE, OUTPUT_DATE_DD_MMM_YYYY);
    }

    public static String convertUTCMillisToDateTime(@Nullable String utcMillisStr) {
        if (utcMillisStr != null) {
            try {
                long utcMillis = Long.parseLong(utcMillisStr);
                Date date = new Date(utcMillis);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_DATE_TIME_LAST_LOGIN, Locale.getDefault());
                return outputDateFormat.format(date);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String convertUTCMillisToTime(@Nullable String utcMillisStr) {
        if (utcMillisStr != null) {
            try {
                long utcMillis = Long.parseLong(utcMillisStr);
                Date date = new Date(utcMillis);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_DATE_TIME_LAST_LOGIN, Locale.getDefault());
                String dateTime = outputDateFormat.format(date);
                String[] arr = dateTime.split(" ");
                return arr[1] + " " + arr[2];
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getCurrentLocalTime() {
        try {
            Calendar c = Calendar.getInstance();
            Date date = c.getTime();
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_DATE_TIME_LAST_LOGIN, Locale.getDefault());
            String dateTime = outputDateFormat.format(date);
            String[] arr = dateTime.split(" ");
            return arr[1] + " " + arr[2];
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
