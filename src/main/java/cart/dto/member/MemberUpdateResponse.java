package cart.dto.member;

import javax.validation.constraints.NotNull;

public class MemberUpdateResponse {

    @NotNull
    private final String name;
    @NotNull
    private final String phoneNumber;
    @NotNull
    private final String password;

    public MemberUpdateResponse(final String name, final String phoneNumber, final String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
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
