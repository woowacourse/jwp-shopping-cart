package cart.dto;

public class ProductId {

    private Long id;

    private ProductId() {

    }

    public ProductId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
