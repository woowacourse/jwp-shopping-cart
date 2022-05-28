package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String email;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthDay;
    private String contact;
    private AddressResponse fullAddress;

    public CustomerResponse() {}

    public CustomerResponse(String email, String profileImage, String name, String gender, String birthDay,
                            String phoneNumber, String address, String detailAddress, String zipCode) {
        this.email = email;
        this.profileImageUrl = profileImage;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = phoneNumber;
        this.fullAddress = new AddressResponse(address, detailAddress, zipCode);
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getContact() {
        return contact;
    }

    public AddressResponse getFullAddress() {
        return fullAddress;
    }
}
