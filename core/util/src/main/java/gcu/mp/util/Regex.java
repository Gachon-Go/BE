package gcu.mp.util;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 형식 체크하는 곳 */
public class Regex {
    /* 가천대 이메일 인지 */
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@gachon.ac.kr";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}