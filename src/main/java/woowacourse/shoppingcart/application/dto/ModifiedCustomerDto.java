package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.Birthday;
import woowacourse.shoppingcart.domain.customer.Contact;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Gender;
import woowacourse.shoppingcart.domain.customer.Name;
import woowacourse.shoppingcart.domain.customer.password.PasswordFactory;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;
import woowacourse.shoppingcart.dto.request.AddressRequest;
import woowacourse.shoppingcart.dto.request.ModifiedCustomerRequest;

public class ModifiedCustomerDto {

    private String email;
    private String password;
    private String profileImageUrl;
    private String name;
    private String gender;
    private String birthday;
    private String contact;
    private AddressRequest address;
    private boolean terms;

    public ModifiedCustomerDto(String email, String password, String profileImageUrl, String name, String gender,
                               String birthday, String contact, AddressRequest address, boolean terms) {
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

    public static ModifiedCustomerDto fromModifiedCustomerRequest(final ModifiedCustomerRequest request) {
        return new ModifiedCustomerDto(request.getEmail(), request.getPassword(), request.getProfileImageUrl(),
                request.getName(), request.getGender(), request.getBirthday(), request.getContact(),
                request.getAddress(), request.isTerms());
    }

    public static Customer toModifiedCustomerDto(final ModifiedCustomerDto modifiedCustomerDto) {
        return new Customer(new Email(modifiedCustomerDto.getEmail()),
                PasswordFactory.of(PasswordType.HASHED, modifiedCustomerDto.getPassword()),
                modifiedCustomerDto.getProfileImageUrl(), new Name(modifiedCustomerDto.getName()),
                Gender.form(modifiedCustomerDto.getGender()),
                new Birthday(modifiedCustomerDto.getBirthday()), new Contact(modifiedCustomerDto.getContact()),
                new FullAddress(modifiedCustomerDto.getAddress().getAddress(),
                        modifiedCustomerDto.getAddress().getDetailAddress(),
                        modifiedCustomerDto.getAddress().getZonecode()));
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
