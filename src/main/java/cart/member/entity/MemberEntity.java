package cart.member.entity;

public class MemberEntity {

    private final int id;
    private final String email;
    private final String password;

    public MemberEntity(final int id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
