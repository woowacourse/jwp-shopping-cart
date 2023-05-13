package cart.business.domain;

import java.util.List;

public class Carts {
    //TODO: cart에 담아야할듯 

    private final List<Cart> carts;

    public Carts(List<Cart> carts) {
        this.carts = carts;
    }

    public void addCart(Cart cart) {
        carts.add(cart);
    }

    public Cart findCartByMemberId(Integer memberId) {
        for (Cart cart : carts) {
            if (memberId == cart.getMemberId()) {
                return cart;
            }
        }
        throw new IllegalArgumentException("memberId에 해당하는 cart가 없습니다");
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
