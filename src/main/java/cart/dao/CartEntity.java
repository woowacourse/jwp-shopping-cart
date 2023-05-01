package cart.dao;

public class CartEntity {

    private final Long id;

    public CartEntity(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
