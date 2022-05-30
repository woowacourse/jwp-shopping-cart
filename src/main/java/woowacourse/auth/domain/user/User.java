package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.domain.user.privacy.Privacy;
import woowacourse.auth.exception.DisagreeToTermsException;

public class User {
    private final Id id;
    private final Email email;
    private final Password password;
    private final ProfileImageUrl profileImageUrl;
    private final Privacy privacy;
    private final boolean terms;

    public User(Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy, boolean terms) {
        this(Id.empty(), email, password, profileImageUrl, privacy, terms);
    }

    public User(Id id, Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy,
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
        User user = (User) o;
        return terms == user.terms && Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password) && Objects.equals(profileImageUrl,
                user.profileImageUrl) && Objects.equals(privacy, user.privacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, profileImageUrl, privacy, terms);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                ", profileImageUrl=" + profileImageUrl +
                ", privacy=" + privacy +
                ", terms=" + terms +
                '}';
    }
}
