package cart.domain;

public class Member {
    private long id;
    private String name;
    private String email;
    private String password;

    public Member(final long id, final String name, final String email, final String password) {
        this(name, email, password);
        this.id = id;
    }

    public Member(final String name, final String email, final String password) {
        validate(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void validate(final String name, final String email, final String password) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 null 또는 공백일 수 없습니다.");
        }
        if (email.isBlank()) {
            throw new IllegalArgumentException("이메일 주소는 null 또는 공백일 수 없습니다.");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("패스워드는 null 또는 공백일 수 없습니다.");
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
