package cart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = ImageUrl.from(imageUrl);
        this.price = Price.from(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }

    // TODO updateName, updateImageUrl, updatePrice 로 나누어 관리할지, 혹은 아래와 같이 하나의 메서드로 처리할지 고민입니다.
    // 나누어 처리한다면 이후 특정 필드는 없데이트 할 수 없게 변경되거나, 업데이트되어야 할 항목들이 추가되는 경우에
    // 기존 메서드의 수정 없이 메서드를 추가함으로써 기능을 추가할 수 있다는 장점이 있을 것 같은데요,
    // 그에 비애 아래와 같이 하나의 update로 처리하는 방식은 코드가 간결해질 뿐만 아니라, 어떤 것들을 업데이트 할 수 있는지
    // 하나의 메서드로 바로 파악이 가능하다는 장점이 있을 것 같습니다.
    // 따라서 특정 필드를 까먹고 업데이트 하지 않는 상황도 발생하지 않을 것 같아요!
    // 완태는 어떠한 방식을 선호하시나요?
    // 또 updateName 이라는 네이밍은 결국 setName과 역할이 동일한데요,
    // 이런 경우에 updateName을 사용하지는지 setName을 사용하시는지 궁금합니다!
    public Product update(final String name, final String imageUrl, final int price) {
        return new Product(id, name, imageUrl, price);
    }
}
