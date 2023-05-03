package cart.entity.member;

public class Member {

    private final Long id;
    private final Email email;
    private final String password;

    public Member(final Long id, final Email email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
