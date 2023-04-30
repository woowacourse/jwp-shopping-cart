package cart.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class MemberResponse {

    @NotNull
    private final String email;
    @NotNull
    private final String name;
    @NotNull
    @JsonProperty("phone-number")
    private final String phoneNumber;
    @NotNull
    private final String password;

    public MemberResponse(final String email, final String name, final String phoneNumber, final String password) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
