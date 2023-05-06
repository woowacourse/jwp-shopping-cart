package cart.service.dto;

import cart.controller.dto.UserSaveRequest;

public class UserSaveDto {

    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;

    private UserSaveDto(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static UserSaveDto from(UserSaveRequest userSaveRequest) {
        return new UserSaveDto(
                userSaveRequest.getEmail(),
                userSaveRequest.getPassword(),
                userSaveRequest.getName(),
                userSaveRequest.getPhoneNumber()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
