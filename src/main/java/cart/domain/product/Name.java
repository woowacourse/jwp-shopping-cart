package cart.domain.product;

import cart.exception.NameCreateFailException;

import java.util.Objects;

public class Name {

    private String name;

    public Name(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name.isBlank()) {
            throw new NameCreateFailException();
        }
    }

    public void edit(final String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
