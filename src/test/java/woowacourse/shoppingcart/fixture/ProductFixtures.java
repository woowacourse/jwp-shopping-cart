package woowacourse.shoppingcart.fixture;

import java.util.HashMap;
import java.util.Map;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;

public class ProductFixtures {
    public static final ProductRequest PRODUCT_REQUEST_1 = new ProductRequest(
            "[든든] 전처리 양파 다이스 15mm(1.5*1.5*1.5/국내산) 1KG", 3940,
            "https://user-images.githubusercontent.com/44823900/167772500-dff4dfb5-6ad2-48fe-937d-81bc6800d0e2.jpg",
            "전처리 양파 다이스", 76);

    public static final ProductRequest PRODUCT_REQUEST_2 = new ProductRequest(
            "맛있는 라면", 1300,
            "https://user-images.githubusercontent.com/44823900/167772500-dff4dfb5-6ad2-48fe-937d-lamen.jpg",
            "개매움", 80);

    public static final ProductRequest PRODUCT_REQUEST_3 = new ProductRequest(
            "아이패드", 1300000,
            "https://user-images.githubusercontent.com/44823900/16777d2-48fe-937d-lamen.jpg",
            "튼튼해요", 70);


    public static final Product PRODUCT_1 = new Product(1L, PRODUCT_REQUEST_1.getName(), PRODUCT_REQUEST_1.getPrice(),
            PRODUCT_REQUEST_1.getImageUrl(), PRODUCT_REQUEST_1.getDescription(), PRODUCT_REQUEST_1.getStock());

    public static final Product PRODUCT_2 = new Product(2L, PRODUCT_REQUEST_2.getName(), PRODUCT_REQUEST_2.getPrice(),
            PRODUCT_REQUEST_2.getImageUrl(), PRODUCT_REQUEST_2.getDescription(), PRODUCT_REQUEST_2.getStock());

    public static final Product PRODUCT_3 = new Product(3L, PRODUCT_REQUEST_3.getName(), PRODUCT_REQUEST_3.getPrice(),
            PRODUCT_REQUEST_3.getImageUrl(), PRODUCT_REQUEST_3.getDescription(), PRODUCT_REQUEST_3.getStock());


    public static Map<String, Object> getProductRequestParam(ProductRequest productRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", productRequest.getName());
        params.put("price", productRequest.getPrice());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("description", productRequest.getDescription());
        params.put("stock", productRequest.getStock());

        return params;
    }
}
