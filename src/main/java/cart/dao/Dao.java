package cart.dao;

import java.util.List;

public interface Dao<T> {
    T findById(Long id);

    List<T> findAll();
    void save(T t);

    void update(T t);

    void deleteById(Long id);
}
