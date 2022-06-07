package woowacourse.auth.dto;

public class PasswordDto {
    private String password;

    public PasswordDto() {
    }

    public PasswordDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
