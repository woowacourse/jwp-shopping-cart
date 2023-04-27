package cart.domain.member;

public class Member {

    private final Email email;
    private final Password password;

    private Member(final Email email, final Password password) {
        this.email = email;
        this.password = password;
    }

    public static Member from(final String email, final String password) {
        return new Member(new Email(email), new Password(password));
    }
}
