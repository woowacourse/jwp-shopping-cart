package woowacourse.shoppingcart.domain.customer;

import java.util.OptionalInt;

public class Validator {
    public static boolean isContainsNumber(String text) {
        OptionalInt result = text.chars()
                .filter(Character::isDigit)
                .findAny();

        return result.isPresent();
    }

    public static boolean isContainsUpperCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isUpperCase(ch))
                .findAny();

        return result.isPresent();
    }

    public static boolean isContainsLowerCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isLowerCase(ch))
                .findAny();

        return result.isPresent();
    }

    public static boolean isContainsSpecialCase(String text) {
        String special = "!@#$%^&*-_";
        OptionalInt result = text.chars()
                .filter(ch -> special.indexOf(ch) != -1)
                .findAny();

        return result.isPresent();
    }
}
