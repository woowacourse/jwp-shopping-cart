package woowacourse.auth.dto;

public class EmailAuthentication {

    private String email;

    public EmailAuthentication() {
    }

    public EmailAuthentication(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
