package cart.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, R> {

    long add(R entity);

    List<E> findAll();

    Optional<E> findById(Long id);

    int updateById(Long id, R request);

    int deleteById(Long id);
}
