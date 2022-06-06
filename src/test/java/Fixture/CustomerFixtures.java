package Fixture;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.password.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.password.RawPassword;
import woowacourse.shoppingcart.domain.customer.password.SHA256Encoder;
import woowacourse.shoppingcart.dto.customer.request.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.customer.request.CustomerUpdateRequest;

public class CustomerFixtures {
    private static final PasswordEncoder passwordEncoder = new SHA256Encoder();

    public static final String MAT_USERNAME = "hyeonic";
    public static final String MAT_EMAIL = "dev.hyeonic@gmail.com";
    public static final RawPassword MAT_RAW_PASSWORD = new RawPassword("1q2w3e4r!");
    public static final EncodedPassword MAT_ENCODED_PASSWORD = passwordEncoder.encode(MAT_RAW_PASSWORD);
    public static final String MAT_ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    public static final String MAT_PHONE_NUMBER = "010-0000-0000";

    public static final Customer MAT = new Customer(MAT_USERNAME, MAT_EMAIL, MAT_ENCODED_PASSWORD, MAT_ADDRESS,
            MAT_PHONE_NUMBER);

    public static final CustomerSaveRequest MAT_SAVE_REQUEST = new CustomerSaveRequest(
            MAT_USERNAME, MAT_EMAIL, MAT_RAW_PASSWORD.getValue(), MAT_ADDRESS, MAT_PHONE_NUMBER);

    public static final TokenRequest MAT_TOKEN_REQUEST = new TokenRequest(MAT_USERNAME, MAT_RAW_PASSWORD.getValue());

    public static final String YAHO_USERNAME = "pup-paw";
    public static final String YAHO_EMAIL = "pup-paw@gmail.com";
    public static final RawPassword YAHO_RAW_PASSWORD = new RawPassword("q1w2e3r4!");
    public static final EncodedPassword YAHO_ENCODED_PASSWORD = passwordEncoder.encode(YAHO_RAW_PASSWORD);
    public static final String YAHO_ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    public static final String YAHO_PHONE_NUMBER = "010-0000-0000";

    public static final Customer YAHO = new Customer(YAHO_USERNAME, YAHO_EMAIL, YAHO_ENCODED_PASSWORD, YAHO_ADDRESS,
            YAHO_PHONE_NUMBER);

    public static final CustomerSaveRequest YAHO_SAVE_REQUEST = new CustomerSaveRequest(
            YAHO_USERNAME, YAHO_EMAIL, YAHO_RAW_PASSWORD.getValue(), YAHO_ADDRESS, YAHO_PHONE_NUMBER);

    public static final TokenRequest YAHO_TOKEN_REQUEST = new TokenRequest(YAHO_USERNAME, YAHO_RAW_PASSWORD.getValue());

    public static final String UPDATE_ADDRESS = "서울 송파구 올림픽로 35다길 42, 루터회관 14층 (잠실 캠퍼스)";
    public static final String UPDATE_PHONE_NUMBER = "010-1111-1111";

    public static final CustomerUpdateRequest UPDATE_REQUEST = new CustomerUpdateRequest(
            UPDATE_ADDRESS, UPDATE_PHONE_NUMBER);
}
