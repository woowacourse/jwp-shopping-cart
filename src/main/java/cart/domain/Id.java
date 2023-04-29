package cart.domain;

public class Id {

    private final Long id;

    public Id(Long id) {
        this.id = id;
    }

    public static Id of(Long id) {
        return new Id(id);
    }

    public Long getId() {
        return id;
    }
}
