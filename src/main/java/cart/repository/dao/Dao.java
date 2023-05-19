package cart.repository.dao;

import java.util.List;

public interface Dao<T> {

    Long save(final T t);

    T findById(final Long id);

    List<T> findAll();

    int update(final T t);

    int delete(final Long id);
}
