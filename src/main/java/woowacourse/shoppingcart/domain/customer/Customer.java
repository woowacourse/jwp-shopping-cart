package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.address.FullAddress;

public class Customer {

    private final Long id;
    private final Email email;
    private final Password password;
    private final String profileImageUrl;
    private final Name name;
    private final Gender gender;
    private final Birthday birthDay;
    private final Contact contact;
    private final FullAddress fullAddress;

    public Customer(Long id, Email email, Password password, String profileImageUrl,
                    Name name, Gender gender, Birthday birthDay, Contact contact,
                    FullAddress fullAddress) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
        this.fullAddress = fullAddress;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Birthday getBirthDay() {
        return birthDay;
    }

    public Contact getContact() {
        return contact;
    }

    public FullAddress getFullAddress() {
        return fullAddress;
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
