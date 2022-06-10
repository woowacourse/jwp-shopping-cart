package woowacourse.shoppingcart.domain;

import java.util.List;
import java.util.function.Supplier;

public class LazyOrders extends Orders {

    private Supplier<List<OrderDetail>> detailLoader;

    public LazyOrders(final Long id, final Long memberId,
                      final Supplier<List<OrderDetail>> detailLoader) {
        super(id, memberId);
        this.detailLoader = detailLoader;
    }

    @Override
    public List<OrderDetail> getOrderDetails() {
        if (super.getOrderDetails() != null) {
            return super.getOrderDetails();
        }

        final List<OrderDetail> orderDetails = detailLoader.get();
        super.lazyInitializeDetails(orderDetails);
        return orderDetails;
    }
}
