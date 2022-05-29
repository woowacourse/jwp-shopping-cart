package woowacourse;

import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateRequest;

public class ShoppingCartFixture {
    public static final CustomerRequest 잉_회원생성요청 = new CustomerRequest("잉", "ing@woowahan.com", "ing_woowahan");
    public static final CustomerUpdateRequest 잉_회원수정요청 = new CustomerUpdateRequest("잉", "ing_woowahan");
    public static final String CUSTOMER_URI = "/api/customer";

    public static final String LOGIN_URI = "/api/login";
}
