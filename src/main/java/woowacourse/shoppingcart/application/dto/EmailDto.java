package woowacourse.shoppingcart.application.dto;

import woowacourse.auth.dto.EmailAuthentication;

public class EmailDto {

    private String email;

    public EmailDto() {
    }

    public EmailDto(String email) {
        this.email = email;
    }

    public static EmailDto from(final EmailAuthentication emailAuthentication) {
        return new EmailDto(emailAuthentication.getEmail());
    }

    public String getEmail() {
        return email;
    }
}
