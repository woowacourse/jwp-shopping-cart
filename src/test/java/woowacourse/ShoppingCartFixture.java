package woowacourse;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.request.ProductRequest;

public class ShoppingCartFixture {
    public static final CustomerRequest 잉_회원생성요청 = new CustomerRequest("잉", "ing@woowahan.com", "*#A123456");
    public static final CustomerUpdateProfileRequest 잉_이름수정요청 = new CustomerUpdateProfileRequest("잉 이름 수정");
    public static final CustomerUpdatePasswordRequest 잉_비밀번호수정요청 = new CustomerUpdatePasswordRequest("*#A123456",
            "*#A12345678");
    public static final TokenRequest 잉_로그인요청 = new TokenRequest(잉_회원생성요청.getEmail(), 잉_회원생성요청.getPassword());
    public static final CustomerDeleteRequest 잉_회원탈퇴요청 = new CustomerDeleteRequest("*#A123456");
    public static final String CUSTOMER_URI = "/api/customer";
    public static final String LOGIN_URI = "/api/login";
    public static final String PRODUCT_URI = "api/products";

    public static final ProductRequest 제품_추가_요청1 = new ProductRequest("치킨", 20000, "url");
    public static final ProductRequest 제품_추가_요청2 = new ProductRequest("피자", 30000, "url");
}
