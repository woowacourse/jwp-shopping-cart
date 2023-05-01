package cart.persistence.entity;

public class MemberEntity {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final String telephone;

    public MemberEntity(final String email, final String password, final String nickname,
                        final String telephone) {
        this(null, email, password, nickname, telephone);
    }

    public MemberEntity(final Long id, final String email, final String password,
                        final String nickname, final String telephone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelephone() {
        return telephone;
    }
}
