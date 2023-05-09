package cart.dto;

import cart.domain.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartDto {

    private final long memberId;
    private final List<ProductDto> products;


    public CartDto(long memberId, List<ProductDto> products) {
        this.memberId = memberId;
        this.products = products;
    }

    public CartDto(Cart cart) {
        this.memberId = cart.getMemberId();
        List<ProductDto> products = cart.getProducts().stream()
                .map(product -> new ProductDto(product))
                .collect(Collectors.toList());
        this.products = products;
    }

    public long getMemberId() {
        return memberId;
    }

    public List<ProductDto> getProducts() {
        return products;
    }
}
