package cart.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberRequest {
    private static final String PHONE_REGEX = "\\d{3}-\\d{4}-\\d{4}";

    @Email(message = "이메일 형식이여야합니다.")
    private String email;

    private String password;

    @NotNull(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @Pattern(regexp = PHONE_REGEX, message = "전화번호 형태는 다음과 같습니다. '010-1234-5678'")
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
