package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private Long id;
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

    public CustomerRequest() {
    }

    public CustomerRequest(Long id, String email, String profileImageUrl, String name, String gender,
                           String birthday, String contact, String address, String detailAddress, String zonecode) {
        this.id = id;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zonecode;
        this.terms = true;
    }

    public Long getId() {
        return id;
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
}
