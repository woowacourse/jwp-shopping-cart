package cart.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Long save(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    int update(T t);

    int deleteById(long id);

    List<T> findProductsByUser(String email);
}
