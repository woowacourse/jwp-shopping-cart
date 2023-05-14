package cart.entity;

public class ProductEntity {
    private static final int MAX_NAME_LENGTH_IN_DB = 20;
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public ProductEntity(final Integer id, final String name, final String image, final Integer price) {
        validate(name, image, price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity(final String name, final String image, final Integer price) {
        this(null, name, image, price);
    }

    private void validate(final String name, final String image, final Integer price) {
        validateName(name);
        validateImage(image);
        validatePrice(price);
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("DB 테이블의 name 은 not null 로 설정 되어 있습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH_IN_DB) {
            throw new IllegalArgumentException("DB 테이블에 이름의 최대 길이는 20 입니다.");
        }
    }

    private void validateImage(final String image) {
        if (image == null) {
            throw new IllegalArgumentException("DB 테이블의 image 는 not null 로 설정 되어 있습니다.");
        }
    }

    private void validatePrice(final Integer price) {
        if (price == null) {
            throw new IllegalArgumentException("DB 테이블의 price 는 not null 로 설정 되어 있습니다.");
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
