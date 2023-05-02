package cart.business.domain.member;

public class Member {

    private final Integer id;
    private final MemberEmail email;
    private final MemberPassword password;

    public Member(Integer id, MemberEmail email, MemberPassword password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
