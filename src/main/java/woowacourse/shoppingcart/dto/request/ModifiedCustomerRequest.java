package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.dto.request.AddressRequest;

public class ModifiedCustomerRequest {

    private String email;
    private String password;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private AddressRequest address;
    private boolean terms;

    public ModifiedCustomerRequest() {
    }

    public ModifiedCustomerRequest(String email, String password, String profileImageUrl, String name, String gender,
                                   String birthday, String contact, String address, String detailAddress,
                                   String zoneCode,
                                   boolean terms) {
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.address = new AddressRequest(address, detailAddress, zoneCode);
        this.terms = terms;
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

    public AddressRequest getAddress() {
        return address;
    }

    public boolean isTerms() {
        return terms;
    }
}
