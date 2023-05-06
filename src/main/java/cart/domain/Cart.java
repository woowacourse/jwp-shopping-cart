package cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private static final int NOT_EXIST_ITEM = 0;
    private final Integer memberId;
    private final List<Product> products;

    public Cart(final Integer memberId, final List<Product> products) {
        validate(memberId, products);
        this.memberId = memberId;
        this.products = new ArrayList<>(products);
    }

    private void validate(final Integer memberId, final List<Product> products) {
        if (memberId == null) {
            throw new IllegalArgumentException("카트는 어떤 유저의 Cart 인지 명시하기 위해 Id 가 필수입니다.");
        }
        if (products == null) {
            throw new IllegalArgumentException("카트는 상품들의 리스트를 가져야 합니다.");
        }
    }


    public void add(final Product product) {
        checkDuplication(product);
        products.add(product);
    }

    private void checkDuplication(final Product product) {
        if (Collections.frequency(products, product) != NOT_EXIST_ITEM) {
            throw new IllegalStateException("이미 존재하는 상품을 더할 수는 없습니다.");
        }
    }

    public void delete(final Product product) {
        if (products.contains(product)) {
            products.remove(products.indexOf(product));
            return;
        }
        throw new IllegalStateException("장바구니에 없는 상품을 삭제할 수 없습니다.");
    }

    public Integer getMemberId() {
        return memberId;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
