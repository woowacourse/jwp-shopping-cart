package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.dto.CustomerRequest;

public class CustomerDto {

    private final String email;
    private final String password;
    private final String profileImageUrl;
    private final String name;
    private final String gender;
    private final String birthDay;
    private final String contact;
    private final AddressDto address;
    private final boolean terms;

    public CustomerDto(String email, String password, String profileImageUrl, String name, String gender,
                       String birthDay, String contact, AddressDto address, boolean terms) {
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
        this.address = address;
        this.terms = terms;
    }

    public static CustomerDto fromCustomerRequest(final CustomerRequest request) {
        return new CustomerDto(request.getEmail(), request.getPassword(), request.getProfileImageUrl(),
                request.getName(), request.getGender(), request.getBirthDay(), request.getContact(),
                AddressDto.fromAddressRequest(request.getAddress()), request.isTerms());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

//    public AddressDto getAddress() {
//        return address;
//    }

    public String getAddress() {
        return address.getAddress();
    }

    public String getDetailAddress() {
        return address.getDetailAddress();
    }

    public String getZoneCode() {
        return address.getZoneCode();
    }

    public boolean isTerms() {
        return terms;
    }
}
