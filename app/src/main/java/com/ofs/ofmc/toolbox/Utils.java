package com.ofs.ofmc.toolbox;


import com.ofs.ofmc.exceptions.EmptyTextException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Utils on 1/30/17.
 */

public class Utils {
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }
    private static Pattern validUser = Pattern.compile("^[A-Za-z_ ]\\w{5,29}$");
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     *
     * Validates the string with the above and throws exception if the string is empty
     *
     * @param username
     * @return boolean
     * @throws EmptyTextException
     */
    public static boolean validUserName(String username){

        return validUser.matcher(username).matches();
    }

    /**
     * Validates the password string and throws exception if the string is empty
     * @param password
     * @return boolean
     * @throws EmptyTextException
     */
    public static boolean validpassword(String password){

        return password.length()>0;
    }


    /**
     * Validates the email string and throws exception if the string is empty
     * @param emailStr
     * @return
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
