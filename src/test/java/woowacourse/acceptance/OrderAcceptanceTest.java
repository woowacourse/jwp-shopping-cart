package woowacourse.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import com.ori.acceptancetest.SpringBootAcceptanceTest;

import woowacourse.acceptance.RestUtils;
import woowacourse.shoppingcart.ProductInsertUtil;

@DisplayName("주문 관련 기능")
@SpringBootAcceptanceTest
public class OrderAcceptanceTest {

    private static final String USER = "puterism";
    private Long cartId1;
    private Long cartId2;

    @Autowired
    private ProductInsertUtil productInsertUtil;

    @BeforeEach
    public void setUp() {
        RestUtils.signUp("a@gmailcom", "!puterism1", "puterism");
        Long productId1 = productInsertUtil.insert("치킨", 10000, "https://example.com/chicken.jpg");
        Long productId2 = productInsertUtil.insert("맥주", 20000, "https://example.com/beer.jpg");
    }
}
