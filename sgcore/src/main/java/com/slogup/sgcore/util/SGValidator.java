package com.slogup.sgcore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sngjoong on 16. 8. 29..
 */
public class SGValidator {

    public static boolean containsSymbolCharacter(String string) {

        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        return pattern.matcher(string).find();
    }

    public static boolean isValidEmailAddress(String email) {

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
