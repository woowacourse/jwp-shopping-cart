package cart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberRequest {

    public static final String PHONE_REGEX = "^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) - \\d{4}$";

    @Email(message = "이메일 형식이여야합니다.")
    private String email;

    private String password;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @Pattern(regexp = PHONE_REGEX)
    private String phone;

    public MemberRequest() {
    }

    public MemberRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

}
