package woowacourse;

import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.shoppingcart.ui.dto.request.CartCreateRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateProfileRequest;

public class ShoppingCartFixture {
    public static final CustomerRequest 잉_회원생성요청 = new CustomerRequest("잉", "ing@woowahan.com", "ing_woowahan");
    public static final CustomerUpdateProfileRequest 잉_이름수정요청 = new CustomerUpdateProfileRequest("잉 이름 수정");
    public static final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest("ing_woowahan",
            "new_woowahan");
    public static final TokenRequest 잉_로그인요청 = new TokenRequest(잉_회원생성요청.getEmail(), 잉_회원생성요청.getPassword());
    public static final CustomerDeleteRequest 잉_회원탈퇴요청 = new CustomerDeleteRequest("ing_woowahan");
    public static final String CUSTOMER_URI = "/api/customer";
    public static final String LOGIN_URI = "/api/login";
    public static final CartCreateRequest 케이블타이 = new CartCreateRequest(1L);
    public static final CartCreateRequest 목장갑 = new CartCreateRequest(2L);
    public static final CartCreateRequest 팝업카드 = new CartCreateRequest(3L);
    public static final String 장바구니_URI = "/api/customer/carts";
    public static final String 주문_URI = "/api/customer/orders";
}
