package woowacourse.member.application.dto;

public class SignUpServiceRequest {

    private final String email;
    private final String name;
    private final String password;

    public SignUpServiceRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
