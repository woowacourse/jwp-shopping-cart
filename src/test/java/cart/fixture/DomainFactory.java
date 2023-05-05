package cart.fixture;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import java.util.List;

public final class DomainFactory {

    public static final Item MAC_BOOK =
            createItem(1L, "맥북", "https://hello-world.com", 1_500_000);
    public static final Cart MAC_BOOK_CART = createCart(1L, MAC_BOOK);

    public static Item createItem(Long id, String name, String imageUrl, int price) {
        return new Item(id, name, imageUrl, price);
    }

    public static Cart createCart(Long id, Item item) {
        return new Cart(id, List.of(item));
    }

    private DomainFactory() {
    }
}
