package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.customer.Birthday;
import woowacourse.shoppingcart.domain.customer.Contact;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Gender;
import woowacourse.shoppingcart.domain.customer.Name;
import woowacourse.shoppingcart.domain.customer.Terms;
import woowacourse.shoppingcart.domain.customer.password.PasswordFactory;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;
import woowacourse.shoppingcart.dto.request.AddressRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

public class CustomerDto {

    private final String email;
    private final String password;
    private final String profileImageUrl;
    private final String name;
    private final String gender;
    private final String birthday;
    private final String contact;
    private final AddressDto address;
    private final boolean terms;

    public CustomerDto(String email, String password, String profileImageUrl, String name, String gender,
                       String birthday, String contact, AddressDto address, boolean terms) {
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.address = address;
        this.terms = terms;
    }

    public static CustomerDto fromCustomerRequest(final SignUpRequest request) {
        return new CustomerDto(request.getEmail(), request.getPassword(), request.getProfileImageUrl(),
                request.getName(), request.getGender(), request.getBirthday(), request.getContact(),
                AddressDto.fromAddressRequest(new AddressRequest(request.getAddress(), request.getDetailAddress(), request.getZonecode())),
                request.isTerms());
    }

    public static Customer toCustomer(final CustomerDto request) {
        return new Customer(0L, new Email(request.getEmail()),
                PasswordFactory.of(PasswordType.HASHED, request.getPassword()),
                request.getProfileImageUrl(), new Name(request.getName()), Gender.form(request.getGender()),
                new Birthday(request.getBirthday()), new Contact(request.getContact()),
                AddressDto.toFullAddress(request.getAddressDto()), new Terms(request.isTerms()));
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

    public AddressDto getAddressDto() {
        return address;
    }

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
