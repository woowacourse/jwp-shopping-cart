package cart.business.domain.member;

public class MemberPassword {

    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 10;

    private final String password;

    public MemberPassword(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password.length() > MAX_SIZE || password.length() < MIN_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return password;
    }
}
