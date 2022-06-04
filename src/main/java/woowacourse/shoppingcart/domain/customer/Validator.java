package woowacourse.shoppingcart.domain.customer;

import java.util.OptionalInt;

class Validator {

    private Validator() {

    }

    static boolean isContainsNumber(String text) {
        OptionalInt result = text.chars()
                .filter(Character::isDigit)
                .findAny();

        return result.isPresent();
    }

    static boolean isContainsUpperCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isUpperCase(ch))
                .findAny();

        return result.isPresent();
    }

    static boolean isContainsLowerCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isLowerCase(ch))
                .findAny();

        return result.isPresent();
    }

    static boolean isContainsSpecialCase(String text) {
        String special = "!@#$%^&*-_";
        OptionalInt result = text.chars()
                .filter(ch -> special.indexOf(ch) != -1)
                .findAny();

        return result.isPresent();
    }

    static boolean isLowerCase(int ch) {
        return Character.isAlphabetic(ch) && Character.isLowerCase(ch);
    }

    static boolean isDigit(int ch) {
        return Character.isDigit(ch);
    }

    static boolean isUnderBar(int ch) {
        return ch == '_';
    }

}
