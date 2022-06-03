package woowacourse.shoppingcart.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Orders {

    private final Long id;
    private final List<OrderDetail> orderDetails;
}
