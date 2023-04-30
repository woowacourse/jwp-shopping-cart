package cart.persistence.dao;

import java.util.List;

public interface Dao<T> {

    Long save(T t);

    T findByName(String name);

    List<T> findAll();

    void update(T t);

    void deleteById(long id);
}
