package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.domain.user.privacy.Privacy;
import woowacourse.auth.exception.DisagreeToTermsException;

public class User {
    private final Email email;
    private final Password password;
    private final ProfileImageUrl profileImageUrl;
    private final Privacy privacy;
    private final boolean terms;

    private User(Email email, Password password, ProfileImageUrl profileImageUrl, Privacy privacy, boolean terms) {
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.privacy = privacy;
        this.terms = terms;
    }

    public static User of(String email, String password, String profileImageUrl, Privacy privacy, boolean terms) {
        validateTerms(terms);
        return new User(new Email(email), new Password(password), new ProfileImageUrl(profileImageUrl), privacy, terms);
    }

    private static void validateTerms(boolean terms) {
        if (!terms) {
            throw new DisagreeToTermsException();
        }
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
        return terms == user.terms && Objects.equals(email, user.email) && Objects.equals(password,
                user.password) && Objects.equals(profileImageUrl, user.profileImageUrl)
                && Objects.equals(privacy, user.privacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, profileImageUrl, privacy, terms);
    }
}
