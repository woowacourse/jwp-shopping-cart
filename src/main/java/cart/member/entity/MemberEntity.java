package cart.member.entity;

public class MemberEntity {

    private final Integer id;
    private final String email;
    private final String password;

    public MemberEntity(final Integer id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
