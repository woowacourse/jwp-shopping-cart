package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private String email;
    private String password;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthDay;
    private String contact;
    private AddressRequest address;
    private boolean terms;

    public CustomerRequest() {}

    public CustomerRequest(String email, String password, String profileImageUrl, String name, String gender,
                           String birthDay, String contact, String address, String detailAddress, String zoneCode,
                           boolean terms) {
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
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

    public String getBirthDay() {
        return birthDay;
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
