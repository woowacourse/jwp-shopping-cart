package cart.domain.product;


import cart.domain.product.Product;

@SuppressWarnings("SpellCheckingInspection")
public class ProductFixture {

    public static final Product NUNU_ID_PRODUCT = new Product(1L, "누누", "naver.com", 1);
    public static final Product ODO_ID_PRODUCT = new Product(2L, "오도", "naver.com", 1);
    public static final Product ODO_PRODUCT = new Product("오도", "naver.com", 1);


    private ProductFixture() {
    }
}
