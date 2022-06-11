package woowacourse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public class Fixtures {
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
    public static final CustomerRequest CUSTOMER_REQUEST_1 = new CustomerRequest(EMAIL_VALUE_1, PASSWORD_VALUE_1,
            PROFILE_IMAGE_URL_VALUE_1, NAME_VALUE_1, GENDER_MALE, BIRTHDAY_FORMATTED_VALUE_1, CONTACT_VALUE_1,
            ADDRESS_VALUE_1, DETAIL_ADDRESS_VALUE_1, ZONE_CODE_VALUE_1, true);
    public static final CustomerRequest CUSTOMER_REQUEST_2 = new CustomerRequest(EMAIL_VALUE_2, PASSWORD_VALUE_2,
            PROFILE_IMAGE_URL_VALUE_2, NAME_VALUE_2, GENDER_MALE, BIRTHDAY_FORMATTED_VALUE_2, CONTACT_VALUE_2,
            ADDRESS_VALUE_2, DETAIL_ADDRESS_VALUE_2, ZONE_CODE_VALUE_2, true);
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
    public static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJodWRpIiwiaWF0IjoxNjU0NDA1NDkzLCJleHAiOjE2NTQ0MDU0OTN9.azYu1VAO1BkS9u1tfZGK6VlHwi9CPA-CAIgWH5KF1bM\n";
    // product1
    public static final String PRODUCT_NAME_VALUE_1 = "치킨";
    public static final String PRODUCT_DESCRIPTION_VALUE_1 = "치킨 입니다";
    public static final Integer PRODUCT_PRICE_VALUE_1 = 15_000;
    public static final Integer PRODUCT_STOCK_VALUE_1 = 10;
    public static final String PRODUCT_IMAGE_URL_VALUE_1 = "http://image.com/1234";
    // invalid fixtures
    public static final CustomerRequest CUSTOMER_INVALID_REQUEST_1 = new CustomerRequest("seongwoo0513", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "조동현", "male", "1999-03-23", "01012345678", "서울특별시 성동구 왕십리역", "길바닥", "54321", true);
}
