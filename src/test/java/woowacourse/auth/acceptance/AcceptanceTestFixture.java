package woowacourse.auth.acceptance;

import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.dto.SignupRequest;

public class AcceptanceTestFixture {

    public static final SignupRequest 에덴 = new SignupRequest("leo0842", "eden", "Password123!", "address", new PhoneNumber("010", "1234", "5678"));
    public static final SignupRequest 코린 = new SignupRequest("hamcheeseburger", "corinne", "Password123!", "address", new PhoneNumber("010", "1234", "5678"));

}
