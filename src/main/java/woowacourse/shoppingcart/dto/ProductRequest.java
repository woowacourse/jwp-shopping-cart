package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProductRequest {
    public static class OnlyId {
        private Long id;

        public OnlyId(Long id) {
            this.id = id;
        }

        private OnlyId() {
        }

        public Long getId() {
            return id;
        }
    }

    public static class AllProperties {
        private Long id;
        @NotBlank(message = "이름은 빈칸일수 없습니다 : ${validatedValue}")
        private String name;
        @Positive(message = "가격은 0보다 커야합니다 : ${validatedValue}")
        private int price;
        private String imageUrl;

        private AllProperties() {
        }

        public AllProperties(final Long id, final String name, final int price, final String imageUrl) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageUrl = imageUrl;
        }

        public Long getId() {
            return id;
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

        public Product toEntity() {
            return new Product(id, name, price, imageUrl);
        }
    }

}
