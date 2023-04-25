package cart.domain;

public class Name {

    private String name;

    public Name(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백이 되면 안됩니다.");
        }
    }

    public void edit(final String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
