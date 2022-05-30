package woowacourse.member.domain;

import woowacourse.member.infrastructure.PasswordEncoder;
import woowacourse.member.infrastructure.SHA256PasswordEncoder;

public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;

    public Member(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member(String email, String password, String name) {
        this(null, email, password, name);
    }

    public void encodePassword(final PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public boolean authenticate(final String password) {
        return password.equals(this.password);
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

    public String getName() {
        return name;
    }
}
