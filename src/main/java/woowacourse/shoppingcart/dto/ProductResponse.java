package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {
    @JsonProperty("product")
    private ProductDto productDto;

    public ProductResponse() {
    }

    public ProductResponse(ProductDto productDto) {
        this.productDto = productDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }
}
