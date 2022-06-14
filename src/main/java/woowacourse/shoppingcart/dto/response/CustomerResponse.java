package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.CustomerRequest;

public class CustomerResponse {

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

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String email, String profileImageUrl, String name, String gender,
                            String birthday, String contact, String address, String detailAddress, String zoneCode) {
        this.id = id;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zoneCode;
        this.terms = true;
    }

    public static CustomerResponse fromCustomerRequest(final CustomerRequest request) {
        return new CustomerResponse(request.getId(), request.getEmail(), request.getProfileImageUrl(),
                request.getName(),
                request.getGender(), request.getBirthday(), request.getContact(), request.getFullAddress().getAddress(),
                request.getFullAddress().getDetailAddress(), request.getFullAddress().getZoneCode());
    }

    public static CustomerResponse fromCustomer(final Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getProfileImageUrl(),
                customer.getName(), customer.getGender(), customer.getBirthday(), customer.getContact(),
                customer.getAddress(), customer.getDetailAddress(), customer.getZoneCode());
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
