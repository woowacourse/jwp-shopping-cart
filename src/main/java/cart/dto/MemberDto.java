package cart.dto;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    private Long id;
    private String email;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    public MemberDto() {
    }

    public MemberDto(final Long id, final String email, final String name, final String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public MemberDto(final String email, final String name, final String password) {
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
