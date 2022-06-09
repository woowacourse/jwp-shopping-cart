package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

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
        private String name;
        private Integer price;
        private String imageUrl;

        private AllProperties() {
        }

        public AllProperties(final Long id, final String name, final Integer price, final String imageUrl) {
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

        public Integer getPrice() {
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
