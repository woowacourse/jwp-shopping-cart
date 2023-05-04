package cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

public class DummyData {

    public static final Product INITIAL_PRODUCT_ONE = Product.of(
            1L,
            "mouse",
            "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg",
            100_000
    );

    public static final Product INITIAL_PRODUCT_TWO = Product.of(
            2L,
            "keyboard",
            "https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1",
            250_000
    );

    public static final Product DUMMY_PRODUCT_ONE = Product.of(
            1L,
            "dummy",
            "http://",
            10_000
    );

    public static final Member INITIAL_MEMBER_ONE = Member.of(
            1L,
            "test@test.com",
            "test"
    );

    public static final Member INITIAL_MEMBER_TWO = Member.of(
            2L,
            "woowacourse@woowa.com",
            "pobi"
    );

    public static final Member DUMMY_MEMBER_ONE = Member.of(
            3L,
            "dummy@dummy.com",
            "dummy"
    );
}
