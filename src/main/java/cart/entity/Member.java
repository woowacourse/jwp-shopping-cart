package cart.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Member {

    private Long id;

    @Email(message = "email 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    public Member(final Long id, final String email, final String name, final String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Member(final String email, final String name, final String password) {
        this(null, email, name, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
