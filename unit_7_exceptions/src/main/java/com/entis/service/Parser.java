package com.entis.service;

import com.entis.data.Calendar;
import com.entis.data.Time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Parser instance = new Parser();
    private long milliseconds;
    private long seconds;
    private long minutes;
    private long hours;
    private long days;
    private long month = 1;
    private long years;
    private String format = "";

    private Parser() {
    }

    public static Parser getInstance() {
        return instance;
    }

    public Time ParseDayMonthYearHourEtcToCalendarDateFormat(String data, String format) {
        this.format = format;
        String check = data.toLowerCase();
        data = check.replaceAll("( )+", " ");
        parseTimeDaysMonthYearEtc(data);
        data = stringToDateFormat(data);
        parseDateFromDdMmYyyyHhMmSsMSms(data);
        return new Calendar(milliseconds, seconds, minutes, hours, days, month, years);
    }

    private String stringToDateFormat(String time) {
        if (format.equals("dd/mm/yy hh:mm:ss:msmsms")) {
            Matcher selectedDate = Pattern.compile("(\\d\\d|\\d| )"
                    + "[- /.]"
                    + "(\\d\\d)"
                    + "[- /.]"
                    + "(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d| )"
                    + "").matcher(time);
            char splitter = '/';
            if (selectedDate.find()) {
                String castDate = selectedDate.group();
                if (!String.valueOf(castDate.charAt(0)).matches("\\d")) {
                    splitter = castDate.charAt(0);
                }
                if (!String.valueOf(castDate.charAt(1)).matches("\\d")) {
                    splitter = castDate.charAt(1);
                }
                if (!String.valueOf(castDate.charAt(2)).matches("\\d")) {
                    splitter = castDate.charAt(2);
                }
                String[] splitArray = castDate.split(String.valueOf(splitter));
                return splitArray[0] + "/" + splitArray[1] + "/" + splitArray[2];
            }
        }
        if (format.equals("mm/dd/yy hh:mm:ss:msmsms")) {
            Matcher takeDate = Pattern.compile("(\\d\\d|\\d| )"
                    + "[- /.]"
                    + "(\\d\\d)"
                    + "[- /.]"
                    + "(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d| )"
                    + "").matcher(time);
            char spliter = '/';
            if (takeDate.find()) {
                String castDate = takeDate.group();
                if (!String.valueOf(castDate.charAt(0)).matches("\\d")) {
                    spliter = castDate.charAt(0);
                }
                if (!String.valueOf(castDate.charAt(1)).matches("\\d")) {
                    spliter = castDate.charAt(1);
                }
                if (!String.valueOf(castDate.charAt(2)).matches("\\d")) {
                    spliter = castDate.charAt(2);
                }
                String[] splitArray = castDate.split(String.valueOf(spliter));
                String[] outPutSplitArray = new String[splitArray.length];
                outPutSplitArray[0] = splitArray[1];
                outPutSplitArray[1] = splitArray[0];
                outPutSplitArray[2] = splitArray[2];
                return outPutSplitArray[0] + "/" + outPutSplitArray[1] + "/" + outPutSplitArray[2];
            }
        }
        if (format.equals("mmm/dd/yy hh:mm:ss:msmsms")) {
            Matcher takeDate = Pattern.compile(
                    "(янв(?:аря)?|фев(?:раля)?|мар(?:та)?|апр(?:еля)?|мая|июн(?:я)?|июл(?:я)?|авг(?:уста)?|сен(?:тября)?|окт(?:ября)?|ноя(?:бря)?|дек(?:абря)?|"
                            + "january?|february?|march?|april?|may|june?|july?|august?|september?|october?|november?|december?)"
                            + "[- /.]"
                            + "(\\d\\d)"
                            + "[- /.]"
                            + "(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d| )"
                            + "").matcher(time);
            char spliter = '/';
            if (takeDate.find()) {
                String castDate = takeDate.group();
                char[] charsOfCastDate = castDate.toCharArray();
                for (char c : charsOfCastDate) {
                    if (String.valueOf(c).matches("([/]|[ ]|[.]|[-])")) {
                        spliter = c;
                        break;
                    }
                }
                String[] splitArray = castDate.split(String.valueOf(spliter));
                if (splitArray[0].matches("\\w{3,10}")) {
                    switch (splitArray[0]) {
                        case "january":
                            splitArray[0] = "01";
                            break;
                        case "february":
                            splitArray[0] = "02";
                            break;
                        case "march":
                            splitArray[0] = "03";
                            break;
                        case "april":
                            splitArray[0] = "04";
                            break;
                        case "may":
                            splitArray[0] = "05";
                            break;
                        case "june":
                            splitArray[0] = "06";
                            break;
                        case "july":
                            splitArray[0] = "07";
                            break;
                        case "august":
                            splitArray[0] = "08";
                            break;
                        case "september":
                            splitArray[0] = "09";
                            break;
                        case "october":
                            splitArray[0] = "10";
                            break;
                        case "november":
                            splitArray[0] = "11";
                            break;
                        case "december":
                            splitArray[0] = "12";
                            break;
                    }
                } else {
                    switch (splitArray[0]) {
                        case "января":
                            splitArray[0] = "01";
                            break;
                        case "февраля":
                            splitArray[0] = "02";
                            break;
                        case "марта":
                            splitArray[0] = "03";
                            break;
                        case "апреля":
                            splitArray[0] = "04";
                            break;
                        case "мая":
                            splitArray[0] = "05";
                            break;
                        case "июня":
                            splitArray[0] = "06";
                            break;
                        case "июля":
                            splitArray[0] = "07";
                            break;
                        case "августа":
                            splitArray[0] = "08";
                            break;
                        case "сентября":
                            splitArray[0] = "09";
                            break;
                        case "октября":
                            splitArray[0] = "10";
                            break;
                        case "ноября":
                            splitArray[0] = "11";
                            break;
                        case "декабря":
                            splitArray[0] = "12";
                            break;
                    }
                }
                String[] outPutSplitArray = new String[splitArray.length];
                outPutSplitArray[0] = splitArray[1];
                outPutSplitArray[1] = splitArray[0];
                outPutSplitArray[2] = splitArray[2];
                return outPutSplitArray[0] + "/" + outPutSplitArray[1] + "/" + outPutSplitArray[2];
            }
        }
        if (format.equals("dd/mmm/yy hh:mm:ss:msmsms")) {
            Matcher takeDate = Pattern.compile("(\\d\\d|\\d| )"
                    + "[- /.]"
                    + "(янв(?:аря)?|фев(?:раля)?|мар(?:та)?|апр(?:еля)?|мая|июн(?:я)?|июл(?:я)?|авг(?:уста)?|сен(?:тября)?|окт(?:ября)?|ноя(?:бря)?|дек(?:абря)?|"
                    + "january?|february?|march?|april?|may|june?|july?|august?|september?|october?|november?|december?)"
                    + "[- /.]"
                    + "(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d| )"
                    + "").matcher(time);
            char spliter = '/';
            if (takeDate.find()) {
                String castDate = takeDate.group();
                char[] charsOfCastDate = castDate.toCharArray();
                for (char c : charsOfCastDate) {
                    if (String.valueOf(c).matches("([/]|[ ]|[.]|[-])")) {
                        spliter = c;
                        break;
                    }
                }
                String[] splitArray = castDate.split(String.valueOf(spliter));
                if (splitArray[1].matches("\\w{3,10}")) {
                    switch (splitArray[1]) {
                        case "january":
                            splitArray[1] = "01";
                            break;
                        case "february":
                            splitArray[1] = "02";
                            break;
                        case "march":
                            splitArray[1] = "03";
                            break;
                        case "april":
                            splitArray[1] = "04";
                            break;
                        case "may":
                            splitArray[1] = "05";
                            break;
                        case "june":
                            splitArray[1] = "06";
                            break;
                        case "july":
                            splitArray[1] = "07";
                            break;
                        case "august":
                            splitArray[1] = "08";
                            break;
                        case "september":
                            splitArray[1] = "09";
                            break;
                        case "october":
                            splitArray[1] = "10";
                            break;
                        case "november":
                            splitArray[1] = "11";
                            break;
                        case "december":
                            splitArray[1] = "12";
                            break;
                    }
                } else {
                    switch (splitArray[1]) {
                        case "января":
                            splitArray[1] = "01";
                            break;
                        case "февраля":
                            splitArray[1] = "02";
                            break;
                        case "марта":
                            splitArray[1] = "03";
                            break;
                        case "апреля":
                            splitArray[1] = "04";
                            break;
                        case "мая":
                            splitArray[1] = "05";
                            break;
                        case "июня":
                            splitArray[1] = "06";
                            break;
                        case "июля":
                            splitArray[1] = "07";
                            break;
                        case "августа":
                            splitArray[1] = "08";
                            break;
                        case "сентября":
                            splitArray[1] = "09";
                            break;
                        case "октября":
                            splitArray[1] = "10";
                            break;
                        case "ноября":
                            splitArray[1] = "11";
                            break;
                        case "декабря":
                            splitArray[1] = "12";
                            break;
                    }
                }
                return splitArray[0] + "/" + splitArray[1] + "/" + splitArray[2];
            }
        }
        return time;
    }

    private void parseTimeDaysMonthYearEtc(String date) {
        Matcher matcherTimeNumbers = Pattern
                .compile(
                        "([0-9]|0[0-9]|1[0-9]|2[0-3]|)[:]([0-9]|[0-5][0-9]|)[:]([0-9]|[0-5][0-9]|)[:](\\d\\d\\d|\\d\\d|\\d| )")
                .matcher(date);
        if (matcherTimeNumbers.find()) {
            StringBuilder buf = new StringBuilder(matcherTimeNumbers.group());
            if (!buf.toString().matches("\\d\\d:\\d\\d:\\d\\d:\\d\\d\\d")) {
                String[] split = buf.toString().split(":");
                buf = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    if (split[i].matches("")) {
                        split[i] = "00";
                    }
                    if (split[i].matches("\\d")) {
                        split[i] = "0" + split[i];
                    }
                    if (i == split.length - 1) {
                        if (split[i].matches(" ")) {
                            split[i] = "000";
                        }
                        if (split[i].matches("\\d")) {
                            split[i] = "00" + split[i];
                        }
                        if (split[i].matches("\\d\\d")) {
                            split[i] = "0" + split[i];
                        }
                    }
                    if (i == split.length - 1) {
                        buf.append(split[i]);
                        break;
                    }
                    buf.append(split[i]).append(":");
                }
            }
            String firstElement = String.valueOf(buf.charAt(0));
            if (firstElement.equals(":")) {
                buf.insert(0, "00");
            }
            if (firstElement.equals(":")) {
                buf.append("0000");
            }
            char[] charsBuf = buf.toString().toCharArray();
            for (int i = 0; i < charsBuf.length; i++) {
                if (String.valueOf(charsBuf[i]).equals(":")) {
                    if (String.valueOf(charsBuf[i + 1]).equals(":")) {
                        buf = new StringBuilder(
                                buf.substring(0, i + 1) + "01" + buf.substring(i + 1, buf.length()));
                    }
                }
            }
            Character charSplit = buf.toString().toCharArray()[2];
            String[] ddMmYyyyHhMmSsMSmsArray;
            if (charSplit.equals('.')) {
                ddMmYyyyHhMmSsMSmsArray = buf.toString().split("\\.");
            } else {
                ddMmYyyyHhMmSsMSmsArray = buf.toString().split(String.valueOf(charSplit));
            }
            for (int i = 0; i < ddMmYyyyHhMmSsMSmsArray.length; i++) {
                switch (i + 1) {
                    case 1:
                        hours = Long.parseLong(ddMmYyyyHhMmSsMSmsArray[i]);
                        break;
                    case 2:
                        minutes = Long.parseLong(ddMmYyyyHhMmSsMSmsArray[i]);
                        break;
                    case 3:
                        seconds = Long.parseLong(ddMmYyyyHhMmSsMSmsArray[i]);
                        break;
                    case 4:
                        milliseconds = Long.parseLong(ddMmYyyyHhMmSsMSmsArray[i]);
                        break;
                }
            }
        }
    }

    private void parseDateFromDdMmYyyyHhMmSsMSms(String ddMmYyyyHhMmSsMSms) {
        String regexHyphen = "(0[1-9]|[12][0-9]|3[01]|[0-9]|)"
                + "([/])(0[1-9]|1[012]|[1-9]|)"
                + "([/])(\\d\\d\\d\\d|\\d\\d\\d|\\d\\d|\\d| )";
        Matcher matcherDateNumbers = Pattern.compile(regexHyphen)
                .matcher(ddMmYyyyHhMmSsMSms);
        if (matcherDateNumbers.find()) {
            StringBuilder buf = new StringBuilder(matcherDateNumbers.group());
            char charSplitDate = '/';
            char[] charBufArrays = buf.toString().toCharArray();
            for (char charBufArray : charBufArrays) {
                if (!(String.valueOf(charBufArray).matches("[0-9]"))) {
                    charSplitDate = charBufArray;
                    break;
                }
            }
            if (!buf.toString().matches("\\d\\d[/]\\d\\d[/]\\d\\d\\d\\d")) {
                String[] split = buf.toString().split(String.valueOf(charSplitDate));
                buf = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    if (split[i].matches("")) {
                        split[i] = "00";
                    }
                    if (split[i].matches("\\d")) {
                        split[i] = "0" + split[i];
                    }
                    if (i == split.length - 1) {
                        if (split[i].matches("\\d\\d\\d")) {
                            split[i] = "0" + split[i];
                        }
                        if (split[i].matches("\\d\\d")) {
                            split[i] = "00" + split[i];
                        }
                        if (split[i].matches("\\d")) {
                            split[i] = "000" + split[i];
                        }
                        if (split[i].matches("")) {
                            split[i] = "0000" + split[i];
                        }
                    }
                    if (i == split.length - 1) {
                        buf.append(split[i]);
                        break;
                    }
                    buf.append(split[i]).append("/");
                }
            }
            String[] formatArray = buf.toString().split("/");
            for (int i = 0; i < formatArray.length; i++) {
                switch (i + 1) {
                    case 1:
                        days = Long.parseLong(formatArray[i]);
                        break;
                    case 2:
                        month = Long.parseLong(formatArray[i]);
                        break;
                    case 3:
                        years = Long.parseLong(formatArray[i]);
                        break;
                }
            }
        }
    }
}
