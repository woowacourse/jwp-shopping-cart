package cart.domain.exception;

public class WrongEmailFormatException extends RuntimeException {

    public WrongEmailFormatException(Language language) {
        super(language.msg);
    }

    public enum Language {
        EN("E-mail can consist of only numbers, lowercase letters, and _"),
        KO("이메일은 숫자, 알파벳 소문자, _로만 구성할 수 있습니다."),
        NONE("");

        private final String msg;

        Language(String msg) {
            this.msg = msg;
        }
    }

}
