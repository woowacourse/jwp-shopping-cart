package cart.presentation.dto;

import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductRequest {

    private String name;
    private String url;
    private Integer price;

    public ProductRequest(String name, String url, Integer price) {
        validateParameters(name, url, price);
        this.name = name;
        this.url = url;
        this.price = price;
    }

    private void validateParameters(String name, String url, Integer price) {
        new ProductName(name);
        new ProductImage(url);
        new ProductPrice(price);
    }

    @JsonCreator
    public static ProductRequest create(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ProductRequest.class);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPrice() {
        return price;
    }
}
