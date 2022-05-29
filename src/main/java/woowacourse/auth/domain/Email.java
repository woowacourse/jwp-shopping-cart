package woowacourse.auth.domain;

import woowacourse.auth.exception.InvalidEmailException;

public class Email {

    private static final String DOT_COM = ".com";
    private static final String AT_SYMBOL = "@";

    private final String value;

    public Email(String value) {
        validateEmailForm(value);
        this.value = value;
    }

    private void validateEmailForm(String value) {
        validateAtSymbol(value);
        validateDotComSuffix(value);
    }

    private void validateAtSymbol(String value) {
        if (!value.contains(AT_SYMBOL)) {
            throw new InvalidEmailException("@ 기호가 없습니다.");
        }
    }

    private void validateDotComSuffix(String value) {
        if (!value.endsWith(DOT_COM)) {
            throw new InvalidEmailException(".com 로 끝나지 않습니다.");
        }
    }
}
