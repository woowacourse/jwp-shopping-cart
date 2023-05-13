package cart.entity.cart;

import cart.exception.common.UnderZeroException;

public class Count {

    private final int count;

    public Count(final int count) {
        validateCountValue(count);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    private void validateCountValue(int count) {
        if (count < 0) {
            throw new UnderZeroException();
        }
    }
}
