package cart.dao;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.dao.DataAccessException;

public class DataAccessExceptionHandler {

    public static <T> Optional<T> handleWithOptional(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(
                    supplier.get()
            );
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }
}
