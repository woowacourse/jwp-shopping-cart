package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInformationException;

public class UserNameValidationImpl implements NameValidation {

    private static final int MAX_NAME_SIZE = 32;
    private static final String NOT_NULL_OR_BLANK = "[ERROR] 사용자 이름은 빈 값일 수 없습니다.";
    private static final String NOT_BLANK_CHARACTER = "[ERROR] 사용자 이름에는 공백이 들어갈 수 없습니다.";
    private static final String NOT_BLANK_PATTERN = "\\S+";

    @Override
    public void validateName(String username) {
        validateNotNull(username);
        validateNotEmpty(username);
        validateNotBlankExist(username);
        validateMaxSize(username);
    }

    private void validateNotNull(String username) {
        if (username == null) {
            throw new InvalidInformationException(NOT_NULL_OR_BLANK);
        }
    }

    private void validateNotEmpty(String username) {
        if (username.isEmpty()) {
            throw new InvalidInformationException(NOT_NULL_OR_BLANK);
        }
    }

    private void validateNotBlankExist(String username) {
        if (!username.matches(NOT_BLANK_PATTERN)) {
            throw new InvalidInformationException(NOT_BLANK_CHARACTER);
        }
    }

    private void validateMaxSize(String username) {
        if (username.length() > MAX_NAME_SIZE) {
            throw new InvalidInformationException("[ERROR] 사용자 이름은 최대 " + MAX_NAME_SIZE + "자 이하여야 합니다.");
        }
    }
}
