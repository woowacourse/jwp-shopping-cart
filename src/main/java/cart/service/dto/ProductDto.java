package cart.service.dto;

public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String image;

    private ProductDto(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.image = builder.image;
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

    public String getImage() {
        return image;
    }

    public static class Builder{
        private Long id;
        private String name;
        private int price;
        private String image;

        public Builder id(final long id){
            this.id = id;
            return this;
        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder price(final int price){
            this.price = price;
            return this;
        }

        public Builder image(final String image){
            this.image = image;
            return this;
        }

        public ProductDto build(){
            return new ProductDto(this);
        }
    }
}
