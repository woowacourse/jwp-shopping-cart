package cart.domain.member;

public class Member {

    private final Name name;
    private final Password password;

    public Member(final String name, final String password) {
        this.name = new Name(name);
        this.password = new Password(password);
    }

    public String getName() {
        return name.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
