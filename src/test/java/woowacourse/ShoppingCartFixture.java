package woowacourse;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateRequest;

public class ShoppingCartFixture {
    public static final CustomerRequest 잉_회원생성요청 = new CustomerRequest("잉", "ing@woowahan.com", "ing_woowahan");
    public static final CustomerUpdateRequest 잉_회원수정요청 = new CustomerUpdateRequest("잉", "ing_woowahan");

    public static final TokenRequest 잉_로그인요청 = new TokenRequest(잉_회원생성요청.getEmail(), 잉_회원생성요청.getPassword());

    public static final CustomerDeleteRequest 잉_회원탈퇴요청 = new CustomerDeleteRequest("ing_woowahan");
    public static final String CUSTOMER_URI = "/api/customer";

    public static final String LOGIN_URI = "/api/login";
}
