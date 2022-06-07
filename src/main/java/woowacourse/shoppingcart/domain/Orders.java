package woowacourse.shoppingcart.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Orders {

    private Long id;
    private List<OrderDetail> orderDetails;
}
