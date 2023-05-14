package cart.service.dto;

import cart.controller.dto.MemberSaveRequest;

public class MemberSaveDto {

    private final String email;
    private final String password;
    private final String name;
    private final String phoneNumber;

    private MemberSaveDto(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static MemberSaveDto from(MemberSaveRequest memberSaveRequest) {
        return new MemberSaveDto(
                memberSaveRequest.getEmail(),
                memberSaveRequest.getPassword(),
                memberSaveRequest.getName(),
                memberSaveRequest.getPhoneNumber()
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
