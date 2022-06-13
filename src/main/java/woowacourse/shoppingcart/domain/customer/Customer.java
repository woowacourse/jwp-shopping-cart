package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.exception.DisagreeToTermsException;

public class Customer {
    private final Long id;
    private final Email email;
    private final Password password;
    private final ProfileImageUrl profileImageUrl;
    private final Privacy privacy;
    private final FullAddress fullAddress;
    private final boolean terms;

    public Customer(Long id, Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy,
                    FullAddress fullAddress,
                    boolean terms) {
        validateTerms(terms);
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.privacy = privacy;
        this.fullAddress = fullAddress;
        this.terms = terms;
    }

    public Customer(Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy,
                    FullAddress fullAddress, boolean terms) {
        this(null, email, password, profileImageUrl, privacy, fullAddress, terms);
    }

    public static Customer of(String email, String password, String profileImageUrl, Privacy privacy,
                              FullAddress fullAddress, boolean terms) {
        return new Customer(new Email(email), Password.fromPlainText(password), new ProfileImageUrl(profileImageUrl),
                privacy, fullAddress, terms);
    }

    private static void validateTerms(boolean terms) {
        if (!terms) {
            throw new DisagreeToTermsException();
        }
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

    public ProfileImageUrl getProfileImageUrl() {
        return profileImageUrl;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public FullAddress getFullAddress() {
        return fullAddress;
    }

    public boolean isTerms() {
        return terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return terms == customer.terms && Objects.equals(id, customer.id) && Objects.equals(email,
                customer.email) && Objects.equals(password, customer.password) && Objects.equals(
                profileImageUrl, customer.profileImageUrl) && Objects.equals(privacy, customer.privacy)
                && Objects.equals(fullAddress, customer.fullAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, profileImageUrl, privacy, fullAddress, terms);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                ", profileImageUrl=" + profileImageUrl +
                ", privacy=" + privacy +
                ", fullAddress=" + fullAddress +
                ", terms=" + terms +
                '}';
    }
}
