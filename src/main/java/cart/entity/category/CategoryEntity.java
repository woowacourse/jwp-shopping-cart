package cart.entity.category;

public class CategoryEntity {

    private Long id;
    private String name;

    public CategoryEntity(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
