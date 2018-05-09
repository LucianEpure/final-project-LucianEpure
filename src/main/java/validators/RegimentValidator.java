package validators;

import dto.RegimentDto;
import dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegimentValidator implements  IValidator{

    private static final int MIN_CODE = 100000;
    private static final int MAX_CODE = 999999;
    private static final int MIN_PASSWORD_LENGTH = 8;


    private final int code;
    private final String password;
    private final List<String> errors;


    public RegimentValidator(int code, String password) {
        this.code = code;
        this.password = password;
        errors = new ArrayList<String>();
    }

    public boolean validate() {
        validatePassword(password);
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private void validateConde(int code){
        if(code<MIN_CODE)
            errors.add("Code too small");
        if(code>MAX_CODE)
            errors.add("Code too big");
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password too short!");
        }
        if (!containsSpecialCharacter(password)) {
            errors.add("Password must contain at least one special character!");
        }
        if (!containsDigit(password)) {
            errors.add("Password must contain at least one number!");
        }
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private static boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}
