package cart.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductEntity {
    private final Long id;
    @NotBlank(message = "빈 값을 허용하지 않습니다.")
    @Size(max = 32)
    private final String name;
    @NotBlank(message = "빈 값을 허용하지 않습니다.")
    private final String imageUrl;
    private final int price;

    public ProductEntity(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
