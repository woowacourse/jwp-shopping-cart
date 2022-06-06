package woowacourse.helper.fixture;

import java.util.List;
import woowacourse.shoppingcart.dto.OrderDetailResponse;

public class OrderFixture {

    private static final OrderDetailResponse ORDER_DETAIL_RESPONSE =
            new OrderDetailResponse(1L,
                    1L,
                    "치킨",
                    100_000,
                    "http://example.com/chicken.jpg",
                    10);

    private static final OrderDetailResponse ORDER_DETAIL_RESPONSE2 =
            new OrderDetailResponse(1L,
                    2L,
                    "맥주",
                    9_000,
                    "http://example.com/beer.jpg",
                    3);

    public static List<OrderDetailResponse> getOrderDetailResponses() {
        return List.of(ORDER_DETAIL_RESPONSE, ORDER_DETAIL_RESPONSE2);
    }
}
