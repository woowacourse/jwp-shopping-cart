package cart.dao;

import java.util.List;

public interface CrudDao<E, R> {

    void add(R entity);

    List<E> findAll();

    E findById(Long id);

    void updateById(Long id);

    void deleteById(Long id);
}
