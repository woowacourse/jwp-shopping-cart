package cart;

import cart.member.domain.Member;
import cart.product.domain.Product;

public class TestFixture {

    public static final  Member NO_ID_MEMBER1 = new Member("email1@email.com", "1234567890a", "01012345678");
    public static final  Member NO_ID_MEMBER2 = new Member("email2@email.com", "1234567890b", "01012345679");
    public static final  Product NO_ID_PRODUCT1 = new Product("product1", "www.image1.com", 3000);
    public static final  Product NO_ID_PRODUCT2 = new Product("product2", "www.image2.com", 10000);
    public static final  Product NO_ID_PRODUCT3 = new Product("product3", "www.image3.com", 0000);
}
