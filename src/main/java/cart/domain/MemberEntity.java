package cart.domain;

public class MemberEntity {

    private final Long id;
    private final Email email;
    private final Password password;

    public MemberEntity(final Long id, final String address, final String password) {
        this.id = id;
        this.email = new Email(address);
        this.password = new Password(password);
    }

    public MemberEntity(final String address, final String password) {
        this(null, address, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getAddress();
    }

    public String getPassword() {
        return password.getValue();
    }
}
