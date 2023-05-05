package cart.dao;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyDao<T, U> {

    List<T> findAll();

    Optional<T> findByUnique(U unique);
}
