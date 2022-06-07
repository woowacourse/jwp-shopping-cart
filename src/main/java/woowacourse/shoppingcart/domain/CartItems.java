package woowacourse.shoppingcart.domain;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CartItems implements Iterable<CartItem> {

    private final long customerId;
    private final List<CartItem> cartItems;

    public CartItems(long customerId, List<CartItem> cartItems) {
        this.customerId = customerId;
        this.cartItems = cartItems;
    }

    public void add(CartItem newCartItem) {
        for (CartItem cartItem : cartItems) {
            cartItem.checkSameProduct(newCartItem);
        }
        cartItems.add(newCartItem);
    }

    public int size() {
        return cartItems.size();
    }

    public boolean contains(CartItem cartItem) {
        return cartItems.contains(cartItem);
    }

    public long getCustomerId() {
        return customerId;
    }

    private class CartItemIterator implements Iterator<CartItem> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < cartItems.size();
        }

        @Override
        public CartItem next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return cartItems.get(current++);
        }
    }

    @Override
    public Iterator<CartItem> iterator() {
        return new CartItemIterator();
    }

    @Override
    public void forEach(Consumer<? super CartItem> action) {
        Iterable.super.forEach(action);
    }
}
