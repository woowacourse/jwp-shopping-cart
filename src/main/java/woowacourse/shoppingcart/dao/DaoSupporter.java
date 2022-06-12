package woowacourse.shoppingcart.dao;

import java.util.function.Supplier;

import org.springframework.jdbc.support.KeyHolder;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class DaoSupporter {

    public static boolean isUpdated(int updatedCount) {
        return updatedCount != 0;
    }

    public static Long getGeneratedId(KeyHolder keyHolder, Supplier<ShoppingCartException> exceptionSupplier) {
        if (keyHolder.getKey() == null) {
            throw exceptionSupplier.get();
        }
        return keyHolder.getKey().longValue();
    }
}
