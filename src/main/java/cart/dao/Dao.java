package cart.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    T insert(T entity);

    void update(T entity);

    boolean isExist(Long id);

    Optional<T> findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
