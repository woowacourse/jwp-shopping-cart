package cart.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    @JsonProperty("phone-number")
    private String phoneNumber;
    @NotNull
    private String password;
}
