package cart.domain.user.validator;

public class PasswordValidator {

    public static void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호 입력이 비어있습니다.");
        }
    }
}
