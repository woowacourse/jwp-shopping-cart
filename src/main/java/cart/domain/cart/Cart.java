package cart.domain.cart;

import cart.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final Member member;
    private final List<Item> items;

    public Cart(Member member, List<Item> items) {
        this.member = member;
        this.items = new ArrayList<>();

        if (items.size() > 0) {
            this.items.addAll(items);
        }
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public Member getMember() {
        return member;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
