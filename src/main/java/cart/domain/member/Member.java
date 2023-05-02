package cart.domain.member;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final MemberName name;

    public Member(String email, String password, String name) {
        this(null, email, password, name);
    }

    public Member(Long id, String email, String password, String name) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new MemberName(name);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getName() {
        return name.getValue();
    }
}
