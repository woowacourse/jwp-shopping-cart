package woowacourse.auth.domain.customer;

import java.util.Objects;
import woowacourse.auth.domain.customer.privacy.Privacy;
import woowacourse.auth.exception.DisagreeToTermsException;

public class Customer {
    private final Id id;
    private final Email email;
    private final Password password;
    private final ProfileImageUrl profileImageUrl;
    private final Privacy privacy;
    private final boolean terms;

    public Customer(Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy, boolean terms) {
        this(Id.empty(), email, password, profileImageUrl, privacy, terms);
    }

    public Customer(Id id, Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy,
                    boolean terms) {
        validateTerms(terms);
        this.id = id;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.privacy = privacy;
        this.terms = terms;
    }

    private static void validateTerms(boolean terms) {
        if (!terms) {
            throw new DisagreeToTermsException();
        }
    }

    public Id getId() {
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
        return terms == customer.terms && Objects.equals(id, customer.id) && Objects.equals(email, customer.email)
                && Objects.equals(password, customer.password) && Objects.equals(profileImageUrl,
                customer.profileImageUrl) && Objects.equals(privacy, customer.privacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, profileImageUrl, privacy, terms);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                ", profileImageUrl=" + profileImageUrl +
                ", privacy=" + privacy +
                ", terms=" + terms +
                '}';
    }
}
