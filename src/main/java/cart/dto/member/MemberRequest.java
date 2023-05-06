package cart.dto.member;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    @NotNull(message = "이메일은 공백일 수 없습니다.")
    private String email;
    @NotNull(message = "유저명은 공백일 수 없습니다.")
    private String name;
    @NotNull(message = "휴대폰 번호는 공백일 수 없습니다.")
    @JsonAlias({"phone-number", "phoneNumber"})
    private String phoneNumber;
    @NotNull(message = "패스워드는 공백일 수 없습니다.")
    private String password;
}
