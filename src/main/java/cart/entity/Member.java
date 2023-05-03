package cart.entity;

import java.util.Objects;

public class Member {

    private Long id;
    private String email;
    private String password;

    public Member() {
    }

    public Member(String email, String password) {
        this(null, email, password);
    }

    public Member(Long id, String email, String password) {
        validateEmail(email);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validateEmail(String email) {
        if (!email.matches("^(\\S+)@(\\S+).(\\S+)$")) {
            throw new IllegalArgumentException("email 형식에 맞지 않습니다");
        }
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

    public boolean matchingPassword(String password) {
        return password.equals(this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
