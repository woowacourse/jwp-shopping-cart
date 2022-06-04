package woowacourse.shoppingcart.dto;

public class CustomerResponse {
    private String email;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private String address;
    private String detailAddress;
    private String zonecode;
    private boolean terms;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String profileImageUrl, String name, String gender, String birthday,
                            String contact, String address, String detailAddress, String zonecode, boolean terms) {
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zonecode;
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

    public String getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getZonecode() {
        return zonecode;
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
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zoneCode='" + zonecode + '\'' +
                ", terms=" + terms +
                '}';
    }
}
