package woowacourse.shoppingcart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.ProfileImageUrl;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.AddressRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public class Fixtures {
    public static CustomerEntity CUSTOMER_ENTITY_1 = new CustomerEntity("devhudi@gmail.com", "a1@12345",
            "http://gravatar.com/avatar/1?d=identicon", true);
    public static PrivacyEntity PRIVACY_ENTITY_1 = new PrivacyEntity("조동현", "male", LocalDate.of(1998, 12, 21),
            "01011112222");
    public static AddressEntity ADDRESS_ENTITY_1 = new AddressEntity("서울특별시 강남구 선릉역", "이디야 1층", "12345");

    public static Privacy PRIVACY_1 = Privacy.of(PRIVACY_ENTITY_1.getName(), PRIVACY_ENTITY_1.getGender(),
            PRIVACY_ENTITY_1.getBirthDay().format(DateTimeFormatter.ISO_DATE), PRIVACY_ENTITY_1.getContact());
    public static Email EMAIL_1 = new Email(CUSTOMER_ENTITY_1.getEmail());
    public static Password PASSWORD_1 = Password.fromPlainText(CUSTOMER_ENTITY_1.getPassword(),
            new BCryptPasswordEncoder());
    public static ProfileImageUrl PROFILE_IMAGE_URL_1 = new ProfileImageUrl(CUSTOMER_ENTITY_1.getProfileImageUrl());


    public static CustomerRequest CUSTOMER_REQUEST_1 = new CustomerRequest("seongwoo0513@example.com", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "박성우", "male", "1999-03-23", "01022223333", new AddressRequest("서울특별시 강남구 선릉역", "이디야 1층", "12345"), true);
}
