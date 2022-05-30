package woowacourse.auth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import woowacourse.auth.domain.customer.Customer;
import woowacourse.auth.domain.customer.Email;
import woowacourse.auth.domain.customer.Password;
import woowacourse.auth.domain.customer.ProfileImageUrl;
import woowacourse.auth.domain.customer.privacy.Privacy;
import woowacourse.auth.entity.CustomerEntity;
import woowacourse.auth.entity.PrivacyEntity;

public class Fixtures {
    public static CustomerEntity CUSTOMER_ENTITY_1 = new CustomerEntity("devhudi@gmail.com", "a1@12345",
            "http://gravatar.com/avatar/1?d=identicon", true);
    public static PrivacyEntity PRIVACY_ENTITY_1 = new PrivacyEntity("조동현", "male", LocalDate.of(1998, 12, 21),
            "01011112222");

    public static Privacy PRIVACY_1 = Privacy.of(PRIVACY_ENTITY_1.getName(), PRIVACY_ENTITY_1.getGender(),
            PRIVACY_ENTITY_1.getBirthDay().format(DateTimeFormatter.ISO_DATE), PRIVACY_ENTITY_1.getContact(),
            "서울특별시 강남구 선릉역", "이디야 1층", "12345");
    public static Email EMAIL_1 = new Email(CUSTOMER_ENTITY_1.getEmail());
    public static Password PASSWORD_1 = Password.fromPlainText(CUSTOMER_ENTITY_1.getPassword(),
            new BCryptPasswordEncoder());
    public static ProfileImageUrl PROFILE_IMAGE_URL_1 = new ProfileImageUrl(CUSTOMER_ENTITY_1.getProfileImageUrl());
    public static Customer CUSTOMER_1 = new Customer(EMAIL_1, PASSWORD_1, PROFILE_IMAGE_URL_1, PRIVACY_1, true);
}
