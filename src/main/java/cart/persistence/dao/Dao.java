package cart.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Long save(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    void update(T t);

    void deleteById(long id);
}
