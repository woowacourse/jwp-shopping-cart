package woowacourse.shoppingcart.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenPayloadDto {

    private Long id;
    private String email;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthDay;
    private String contact;
    private AddressDto fullAddress;

    public TokenPayloadDto() {
    }

    public TokenPayloadDto(Long id, String email, String profileImageUrl, String name, String gender,
                           String birthDay, String contact, String address, String detailAddress, String zoneCode) {
        this.id = id;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
        this.fullAddress = new AddressDto(address, detailAddress, zoneCode);
    }

    public static class AddressDto {

        private String address;
        private String detailAddress;
        private String zoneCode;

        public AddressDto() {
        }

        public AddressDto(String address, String detailAddress, String zoneCode) {
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

    public String getBirthDay() {
        return birthDay;
    }

    public String getContact() {
        return contact;
    }

    public AddressDto getFullAddress() {
        return fullAddress;
    }
}
