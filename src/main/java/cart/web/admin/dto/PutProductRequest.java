package cart.web.admin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import cart.domain.persistence.entity.ProductEntity;

public class PutProductRequest {

    @NotBlank(message = "20자 이하의 상품 명을 입력해주세요")
    @Length(max = 20, message = "20자 이하의 상품 명을 입력해주세요")
    private String name;

    @NotNull(message = "유효한 가격을 입력해주세요")
    @Range(min = 0, max = Integer.MAX_VALUE, message = "유효한 가격을 입력해주세요")
    private Integer price;

    @NotBlank(message = "유효한 이미지 URL을 입력해주세요")
    @Length(max = 512, message = "유효한 이미지 URL을 입력해주세요")
    private String imageUrl;

    private PutProductRequest() {
    }

    public PutProductRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductEntity toEntity(final PutProductRequest request) {
        return new ProductEntity(request.getName(), request.getPrice(), request.getImageUrl());
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
