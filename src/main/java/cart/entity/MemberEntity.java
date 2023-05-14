package cart.entity;

public class MemberEntity {
    private final Integer id;
    private final String email;
    private final String password;

    public MemberEntity(final Integer id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validate(final String email, final String password) {
        if (email == null) {
            throw new IllegalArgumentException("DB 테이블의 email 은 not null 로 설정 되어 있습니다.");
        }
        if (password == null) {
            throw new IllegalArgumentException("DB 테이블의 password 은 not null 로 설정 되어 있습니다.");
        }
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
