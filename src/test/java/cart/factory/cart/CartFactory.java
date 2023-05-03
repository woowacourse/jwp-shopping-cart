package cart.factory.cart;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;

import static cart.factory.cart.CartItemsFactory.createCartItems;
import static cart.factory.member.MemberFactory.createMember;

public class CartFactory {

    public static Cart createCart() {
        return Cart.from(createMember(), createCartItems());
    }

    public static Cart createCart(final Member member, final Product product) {
        return Cart.from(member, createCartItems(product));
    }
}
