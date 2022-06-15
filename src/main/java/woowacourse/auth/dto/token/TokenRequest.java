package woowacourse.auth.dto.token;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
