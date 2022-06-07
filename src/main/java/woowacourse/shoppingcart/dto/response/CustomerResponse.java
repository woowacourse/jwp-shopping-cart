package woowacourse.shoppingcart.dto.response;

public class CustomerResponse {
    private String email;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private AddressResponse fullAddress;
    private boolean terms;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String profileImageUrl, String name, String gender, String birthday,
                            String contact, AddressResponse fullAddress, boolean terms) {
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.fullAddress = fullAddress;
        this.terms = terms;
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

    public String getBirthday() {
        return birthday;
    }

    public String getContact() {
        return contact;
    }

    public AddressResponse getFullAddress() {
        return fullAddress;
    }

    public boolean isTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "email='" + email + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", contact='" + contact + '\'' +
                ", fullAddress=" + fullAddress +
                ", terms=" + terms +
                '}';
    }
}
