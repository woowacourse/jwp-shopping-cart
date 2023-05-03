package cart.domain.member;

public class Member {

    private final Name name;
    private final Password password;

    public Member(final Name name, final Password password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
