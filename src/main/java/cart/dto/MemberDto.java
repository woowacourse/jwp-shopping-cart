package cart.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;

public class MemberDto {

    private final long id;

    @NonNull
    @Email
    private final String email;

    @NonNull
    private final String password;

    public MemberDto(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
