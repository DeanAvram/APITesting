package apitesting;

import java.util.Date;

public class Helper {

    // enum with log types
    public enum LogType {
        START,
        INFO,
        ERROR,
        WARNING,
        DEBUG,
        PASS,
        FAIL
    }

    public static String logHelper(LogType logType, String message) {
        String logTypeString = "";
        logTypeString = logTypeString.concat("[")
                .concat(logType.toString())
                .concat("] ")
                .concat(new Date().toString())
                .concat(" - ")
                .concat(message)
                .concat("\n")
        ;
        return logTypeString;
    }
}
