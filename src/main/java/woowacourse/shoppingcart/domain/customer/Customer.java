package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;

public class Customer {

    private Long id;
    private Email email;
    private Password password;
    private String profileImageUrl;
    private Name name;
    private Gender gender;
    private Birthday birthday;
    private Contact contact;
    private FullAddress fullAddress;
    private Terms terms;

    private Customer() {
    }

    public Customer(Long id, Email email, Password password, String profileImageUrl,
                    Name name, Gender gender, Birthday birthday, Contact contact,
                    FullAddress fullAddress, Terms terms) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
        this.fullAddress = fullAddress;
        this.terms = terms;
    }

    public Customer(Long id, Email email, Password password, String profileImageUrl, Name name,
                    Gender gender, Birthday birthday, Contact contact,
                    FullAddress fullAddress) {
        this(id, email, password, profileImageUrl, name, gender,
                birthday, contact, fullAddress, new Terms(true));
    }

    public Customer(Email email, Password password, String profileImageUrl,
                    Name name, Gender gender, Birthday birthday, Contact contact,
                    FullAddress fullAddress) {
        this(null, email, password, profileImageUrl, name, gender, birthday, contact, fullAddress, new Terms(true));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name.getValue();
    }

    public String getGender() {
        return gender.getValue();
    }

    public String getBirthday() {
        return birthday.getValue();
    }

    public String getContact() {
        return contact.getValue();
    }

    public FullAddress getFullAddress() {
        return fullAddress;
    }

    public String getAddress() {
        return fullAddress.getAddress();
    }

    public String getDetailAddress() {
        return fullAddress.getDetailAddress();
    }

    public String getZoneCode() {
        return fullAddress.getZoneCode();
    }

    public boolean getTerms() {
        return terms.isValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
