package cart.persistence.dao;

import java.util.List;

public interface Dao<T> {

    Long save(T t);

    T findById(Long id);

    List<T> findAll();

    void update(T t);

    void deleteById(long id);
}
