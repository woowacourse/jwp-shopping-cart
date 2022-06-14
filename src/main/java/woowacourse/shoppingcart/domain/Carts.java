package woowacourse.shoppingcart.domain;

import java.util.List;

public class Carts {
    private final List<Cart> elements;

    public Carts(final List<Cart> elements) {
        this.elements = elements;
    }


    public List<Cart> getElements() {
        return elements;
    }

    public boolean haveCartId(final Long cartId) {
        for (final Cart element : elements) {
            if (element.isSameId(cartId)) {
                return true;
            }
        }
        return false;
    }
}
