package woowacourse.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Password;
import woowacourse.auth.domain.user.ProfileImageUrl;
import woowacourse.auth.domain.user.User;
import woowacourse.auth.domain.user.privacy.Privacy;

public class Fixtures {
    public static Privacy PRIVACY_1 = Privacy.of("조동현", "male", "1998-12-21", "01011112222", "서울특별시 강남구 선릉역", "이디야 1층",
            "12345");
    public static Email EMAIL_1 = new Email("devhudi@gmail.com");
    public static Password PASSWORD_1 = Password.fromPlainText("a1@12345", new BCryptPasswordEncoder());
    public static ProfileImageUrl PROFILE_IMAGE_URL_1 = new ProfileImageUrl("http://gravatar.com/avatar/1?d=identicon");
    public static User USER_1 = new User(EMAIL_1, PASSWORD_1, PROFILE_IMAGE_URL_1, PRIVACY_1, true);
}
