package woowacourse.shoppingcart.fixture;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.ProfileImageUrl;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoderAdapter;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public class CustomerFixtures {
    public static CustomerEntity CUSTOMER_ENTITY_1 = new CustomerEntity("devhudi@gmail.com", "a1@12345",
            "http://gravatar.com/avatar/1?d=identicon", true);

    public static PrivacyEntity PRIVACY_ENTITY_1 = new PrivacyEntity("조동현", "male",
            LocalDate.of(1998, 12, 21), "01011112222");

    public static AddressEntity ADDRESS_ENTITY_1 = new AddressEntity("서울특별시 강남구 선릉역", "이디야 1층", "12345");

    public static AddressEntity ADDRESS_ENTITY_2 = new AddressEntity("서울특별시 성동구 왕십리", "마조로 1길", "54321");

    public static Email EMAIL_1 = new Email(CUSTOMER_ENTITY_1.getEmail());

    public static Password PASSWORD_1 = Password.fromPlainText(CUSTOMER_ENTITY_1.getPassword(),
            new PasswordEncoderAdapter());

    public static Password PASSWORD_2 = Password.fromPlainText("a1!12345", new PasswordEncoderAdapter());

    public static Privacy PRIVACY_1 = Privacy.of(PRIVACY_ENTITY_1.getName(), PRIVACY_ENTITY_1.getGender(),
            PRIVACY_ENTITY_1.getBirthday().format(DateTimeFormatter.ISO_DATE), PRIVACY_ENTITY_1.getContact());

    public static Privacy PRIVACY_1_UPDATED_CONTACT = Privacy.of(PRIVACY_ENTITY_1.getName(),
            PRIVACY_ENTITY_1.getGender(),
            PRIVACY_ENTITY_1.getBirthday().format(DateTimeFormatter.ISO_DATE), "01012314567");

    public static FullAddress FULL_ADDRESS_1 = FullAddress.of(ADDRESS_ENTITY_1.getAddress(),
            ADDRESS_ENTITY_1.getDetailAddress(), ADDRESS_ENTITY_1.getZoneCode());

    public static FullAddress FULL_ADDRESS_2 = FullAddress.of(ADDRESS_ENTITY_2.getAddress(),
            ADDRESS_ENTITY_2.getDetailAddress(), ADDRESS_ENTITY_2.getZoneCode());

    public static ProfileImageUrl PROFILE_IMAGE_URL_1 = new ProfileImageUrl(CUSTOMER_ENTITY_1.getProfileImageUrl());

    public static Customer CUSTOMER_1 = new Customer(EMAIL_1, PASSWORD_1, PROFILE_IMAGE_URL_1, PRIVACY_1,
            FULL_ADDRESS_1, true);

    public static Customer CUSTOMER_1_UPDATED_PASSWORD = new Customer(EMAIL_1, PASSWORD_2, PROFILE_IMAGE_URL_1,
            PRIVACY_1,
            FULL_ADDRESS_1, true);

    public static CustomerRequest CUSTOMER_REQUEST_1 = new CustomerRequest("seongwoo0513@example.com", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "박성우", "male", "", "01022223333", "서울특별시 강남구 선릉역", "이디야 1층", "12345", true);

    public static CustomerRequest CUSTOMER_REQUEST_2 = new CustomerRequest("seongwoo0513@example.com", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "조동현", "male", "1999-03-23", "01012345678", "서울특별시 성동구 왕십리역", "길바닥", "54321", true);

    public static CustomerRequest CUSTOMER_INVALID_REQUEST_1 = new CustomerRequest("seongwoo0513", "string&123",
            "http://gravatar.com/avatar/1?d=identicon",
            "조동현", "male", "1999-03-23", "01012345678", "서울특별시 성동구 왕십리역", "길바닥", "54321", true);


    public static TokenRequest TOKEN_REQUEST_1 = new TokenRequest(CUSTOMER_REQUEST_1.getEmail(),
            CUSTOMER_REQUEST_1.getPassword());
}
