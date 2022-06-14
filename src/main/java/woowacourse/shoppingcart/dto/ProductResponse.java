package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String image;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Integer price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static List<ProductResponse> from(List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
