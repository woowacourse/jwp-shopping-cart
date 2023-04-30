package cart.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MemberAddRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phoneNumber;
}
