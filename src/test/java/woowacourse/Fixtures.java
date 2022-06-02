package woowacourse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.AddressRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public class Fixtures {
    public static final PasswordEncoder BCRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    // gender
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_UNDEFINED = "undefined";

    // customer1
    public static final String EMAIL_VALUE_1 = "devhudi@gmail.com";
    public static final String PASSWORD_VALUE_1 = "a1@12345";
    public static final String PROFILE_IMAGE_URL_VALUE_1 = "http://gravatar.com/avatar/1?d=identicon";
    public static final String NAME_VALUE_1 = "조동현";
    public static final LocalDate BIRTHDAY_VALUE_1 = LocalDate.of(1998, 12, 21);
    public static final String BIRTHDAY_FORMATTED_VALUE_1 = BIRTHDAY_VALUE_1.format(DateTimeFormatter.ISO_DATE);
    public static final String CONTACT_VALUE_1 = "01011112222";
    public static final String ADDRESS_VALUE_1 = "서울특별시 강남구 선릉역";
    public static final String DETAIL_ADDRESS_VALUE_1 = "이디야 1층";
    public static final String ZONE_CODE_VALUE_1 = "12345";
    public static final boolean TERMS_1 = true;

    // customer2
    public static final String EMAIL_VALUE_2 = "seongwoo0513@example.com";
    public static final String PASSWORD_VALUE_2 = "string&123";
    public static final String PROFILE_IMAGE_URL_VALUE_2 = "http://gravatar.com/avatar/1?d=identicon";
    public static final String NAME_VALUE_2 = "박성우";
    public static final LocalDate BIRTHDAY_VALUE_2 = LocalDate.of(1999, 3, 23);
    public static final String BIRTHDAY_FORMATTED_VALUE_2 = BIRTHDAY_VALUE_2.format(DateTimeFormatter.ISO_DATE);
    public static final String CONTACT_VALUE_2 = "01022223333";
    public static final String ADDRESS_VALUE_2 = "서울특별시 강남구 선릉역";
    public static final String DETAIL_ADDRESS_VALUE_2 = "길바닥";
    public static final String ZONE_CODE_VALUE_2 = "54321";
    public static final boolean TERMS_2 = true;

    // requests
    public static final AddressRequest ADDRESS_REQUEST_1 = new AddressRequest(ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
            ZONE_CODE_VALUE_1);
    public static final CustomerRequest CUSTOMER_REQUEST_1 = new CustomerRequest(EMAIL_VALUE_1, PASSWORD_VALUE_1,
            PROFILE_IMAGE_URL_VALUE_1, NAME_VALUE_1, GENDER_MALE, BIRTHDAY_FORMATTED_VALUE_1, CONTACT_VALUE_1,
            ADDRESS_REQUEST_1, true);
    public static final AddressRequest ADDRESS_REQUEST_2 = new AddressRequest(ADDRESS_VALUE_2, DETAIL_ADDRESS_VALUE_2,
            ZONE_CODE_VALUE_2);
    public static final CustomerRequest CUSTOMER_REQUEST_2 = new CustomerRequest(EMAIL_VALUE_2, PASSWORD_VALUE_2,
            PROFILE_IMAGE_URL_VALUE_2, NAME_VALUE_2, GENDER_MALE, BIRTHDAY_FORMATTED_VALUE_2, CONTACT_VALUE_2,
            ADDRESS_REQUEST_2, true);
    public static final TokenRequest TOKEN_REQUEST_1 = new TokenRequest(EMAIL_VALUE_1, PASSWORD_VALUE_1);
    public static final TokenRequest TOKEN_REQUEST_2 = new TokenRequest(EMAIL_VALUE_2, PASSWORD_VALUE_2);

    // entities
    public static final CustomerEntity CUSTOMER_ENTITY_1 = new CustomerEntity(EMAIL_VALUE_1, PASSWORD_VALUE_1,
            PROFILE_IMAGE_URL_VALUE_1, true);
    public static final PrivacyEntity PRIVACY_ENTITY_1 = new PrivacyEntity(NAME_VALUE_2, GENDER_MALE, BIRTHDAY_VALUE_1,
            CONTACT_VALUE_1);
    public static final AddressEntity ADDRESS_ENTITY_1 = new AddressEntity(ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1,
            ZONE_CODE_VALUE_1);

    // etc
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU0MDExOTk1LCJleHAiOjE2NTQwMTE5OTV9.L5pnN2Dorp20abb75HFXbYTLxhfFqP4pSfUFu5Rqyzs";

    // invalid fixtures
    public static CustomerRequest CUSTOMER_INVALID_REQUEST_1 = new CustomerRequest("seongwoo0513", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "조동현", "male", "1999-03-23", "01012345678", new AddressRequest("서울특별시 성동구 왕십리역", "길바닥", "54321"), true);
}
