package woowacourse.shoppingcart.dto.request;

public class CustomerRequest {
    private Long id;
    private String email;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private AddressRequest fullAddress;
    private boolean terms;

    public CustomerRequest() {
    }

    public CustomerRequest(Long id, String email, String profileImageUrl, String name, String gender,
                           String birthday, String contact, String address, String detailAddress, String zoneCode) {
        this.id = id;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.fullAddress = new AddressRequest(address, detailAddress, zoneCode);
        this.terms = true;
    }

    public static class AddressRequest {

        private String address;
        private String detailAddress;
        private String zoneCode;

        public AddressRequest() {
        }

        public AddressRequest(String address, String detailAddress, String zoneCode) {
            this.address = address;
            this.detailAddress = detailAddress;
            this.zoneCode = zoneCode;
        }

        public String getAddress() {
            return address;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public String getZoneCode() {
            return zoneCode;
        }
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

    public AddressRequest getFullAddress() {
        return fullAddress;
    }

    public boolean isTerms() {
        return terms;
    }
}
