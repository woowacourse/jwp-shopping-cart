package woowacourse.shoppingcart.dto.response;

import java.util.Objects;

public class UniqueUsernameResponse {

    private boolean unique;

    public UniqueUsernameResponse() {
    }

    public UniqueUsernameResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean getUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UniqueUsernameResponse that = (UniqueUsernameResponse) o;
        return unique == that.unique;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unique);
    }

    @Override
    public String toString() {
        return "UniqueUsernameResponse{" + "unique=" + unique + '}';
    }
}
