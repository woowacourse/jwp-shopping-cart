package cart.product.domain;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;

    public Member(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
