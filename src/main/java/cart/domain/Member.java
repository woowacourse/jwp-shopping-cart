package cart.domain;

public class Member {

    private final int id;

    private final String email;
    private final String password;

    public Member(final int id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void checkPassword(final String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
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
