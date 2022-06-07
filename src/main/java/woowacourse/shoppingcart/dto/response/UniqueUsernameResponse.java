package woowacourse.shoppingcart.dto.response;

import java.util.Objects;

public class UniqueUsernameResponse {

    private boolean isUnique;

    public UniqueUsernameResponse() {
    }

    public UniqueUsernameResponse(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
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
        return isUnique == that.isUnique;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isUnique);
    }

    @Override
    public String toString() {
        return "UniqueUsernameResponse{" + "isUnique=" + isUnique + '}';
    }
}
