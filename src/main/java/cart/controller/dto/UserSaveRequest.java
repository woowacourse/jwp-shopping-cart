package cart.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserSaveRequest {

    @NotNull(message = "이메일은 null일 수 없습니다.")
    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    private final String email;

    @NotNull(message = "비밀번호는 null일 수 없습니다.")
    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{10,}$",
            message = "비밀번호는 영문자, 숫자, 특수문자(\"!@#$%^&*()\")를 모두 최소 1개씩 포함해야 합니다."
    )
    private final String password;

    private final String name;

    private final String phoneNumber;

    public UserSaveRequest(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
