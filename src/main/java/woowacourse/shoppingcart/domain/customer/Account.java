package woowacourse.shoppingcart.domain.customer;

import java.util.Locale;
import java.util.regex.Pattern;

public class Account {

    private static final String EXCLUDE_NUMBER_AND_ALPHABET = "[^\\da-zA-Z]";
    private static final Pattern KOREAN_PATTERN = Pattern.compile("(.*[ㄱ-ㅎ|가-힣]\\w*)");


    private final String value;

    public Account(String account) {
        validateIncludeKorean(account);
        final String processedAccount = removeSpecialCharacter(account);
        validateAccountLength(processedAccount);
        this.value = processedAccount;
    }

    private void validateIncludeKorean(String account) {
        if (KOREAN_PATTERN.matcher(account).matches()) {
            throw new IllegalArgumentException("한글 아이디는 허용되지 않습니다.");
        }
    }

    private void validateAccountLength(String account) {
        if (account.length() < 4 || account.length() > 15) {
            throw new IllegalArgumentException("아이디는 특수문자를 제외하고 4~15자를 만족해야 합니다.");
        }
    }

    private String removeSpecialCharacter(String account) {
        return account.replaceAll(EXCLUDE_NUMBER_AND_ALPHABET, "")
                .toLowerCase(Locale.ROOT).trim();
    }

    public String getValue() {
        return value;
    }
}
