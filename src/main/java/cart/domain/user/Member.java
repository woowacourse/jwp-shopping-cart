package cart.domain.user;

public final class Member {

    private final String email;
    private final String password;

    public Member(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
