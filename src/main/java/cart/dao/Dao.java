package cart.dao;

import java.util.List;

public interface Dao<T> {
    T findById(Long id);

    List<T> findAll();

    Long save(T t);

    int update(T t);

    int deleteById(Long id);
}
