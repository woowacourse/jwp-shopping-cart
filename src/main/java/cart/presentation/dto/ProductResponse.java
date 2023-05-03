package cart.presentation.dto;

import cart.business.domain.ProductImage;
import cart.business.domain.ProductName;
import cart.business.domain.ProductPrice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

public class ProductResponse {

    private Integer id;
    private String name;
    private String url;
    private Integer price;

    public ProductResponse() {
    }

    public ProductResponse(Integer id, String name, String url, Integer price) {
        validateParameters(name, url, price);
        this.id = id;
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
    @NonNull
    public static ProductResponse create(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ProductResponse.class);
    }

    public Integer getId() {
        return id;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.lang.String getUrl() {
        return url;
    }

    public Integer getPrice() {
        return price;
    }
}
