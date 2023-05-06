package cart.controller.dto.response;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import cart.entity.CartEntity;
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
=======
>>>>>>> 339fefaa (feat: findAllByMemberId 테스트 및 테스트 전용 sql 파일 설정)
import cart.entity.ProductEntity;

public class CartItemResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    private CartItemResponse(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static CartItemResponse from(ProductEntity product) {
        return new CartItemResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
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

    public String getImageUrl() {
        return imageUrl;
    }
<<<<<<< HEAD
=======


>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
}
