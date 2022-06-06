package woowacourse.shoppingcart.application.dto;

public class CustomerPasswordUpdateServiceRequest {

    private final Long id;
    private final String oldPassword;
    private final String newPassword;

    public CustomerPasswordUpdateServiceRequest(final Long id,
                                                final String oldPassword,
                                                final String newPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
