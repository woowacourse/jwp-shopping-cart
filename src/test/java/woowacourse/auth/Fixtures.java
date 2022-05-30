package woowacourse.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Password;
import woowacourse.auth.domain.user.ProfileImageUrl;
import woowacourse.auth.domain.user.User;
import woowacourse.auth.domain.user.privacy.Privacy;
import woowacourse.auth.entity.UserEntity;

public class Fixtures {
    public static UserEntity USER_ENTITY_1 = new UserEntity("devhudi@gmail.com", "a1@12345",
            "http://gravatar.com/avatar/1?d=identicon", true);

    public static Privacy PRIVACY_1 = Privacy.of("조동현", "male", "1998-12-21", "01011112222", "서울특별시 강남구 선릉역", "이디야 1층",
            "12345");
    public static Email EMAIL_1 = new Email(USER_ENTITY_1.getEmail());
    public static Password PASSWORD_1 = Password.fromPlainText(USER_ENTITY_1.getPassword(),
            new BCryptPasswordEncoder());
    public static ProfileImageUrl PROFILE_IMAGE_URL_1 = new ProfileImageUrl(USER_ENTITY_1.getProfileImageUrl());
    public static User USER_1 = new User(EMAIL_1, PASSWORD_1, PROFILE_IMAGE_URL_1, PRIVACY_1, true);
}
