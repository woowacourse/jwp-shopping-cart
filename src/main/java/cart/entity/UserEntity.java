package cart.entity;

import java.util.Objects;

public class UserEntity {

    private static final String ANONYMOUS_NAME = "익명의 사용자";

    private final Integer id;
    private final String email;
    private final String password;
    private final String name;

    public UserEntity(final Integer id, final String email, final String password, final String name) {
        validate(email, password, name);
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private void validate(final String email, final String password, final String name) {
        if (Objects.isNull(email) || Objects.isNull(password)) {
            throw new IllegalArgumentException("User 의 email, password 는 null 을 허용하지 않습니다.");
        }
        if (Objects.isNull(name)) {
            throw new RuntimeException("name이 null인 서버 내부 오류입니다.");
        }
    }

    public UserEntity(final String email, final String password, final String name) {
        this(null, email, password, name);
    }

    public UserEntity(final String email, final String password) {
        this(null, email, password, ANONYMOUS_NAME);
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

    public String getName() {
        return name;
    }
}
