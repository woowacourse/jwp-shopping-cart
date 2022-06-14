package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.AddressDto;

public class CustomerDto {

    private Long id;
    private String email;
    private String password;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private AddressDto fullAddress;
    private boolean terms;

    public CustomerDto(Long id, String email, String password, String profileImageUrl, String name, String gender,
                       String birthday, String contact, String address, String detailAddress, String zoneCode
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.fullAddress = new AddressDto(address, detailAddress, zoneCode);
        this.terms = true;
    }

    public Long getId() {
        return id;
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

    public String getBirthday() {
        return birthday;
    }

    public String getContact() {
        return contact;
    }

    public AddressDto getFullAddress() {
        return fullAddress;
    }

    public boolean isTerms() {
        return terms;
    }
}
