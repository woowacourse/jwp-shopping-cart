package cart.dao;

import java.util.List;

public interface Dao<T> {

    int insert(T t);

    List<T> selectAll();

    int update(T t);

    int delete(int id);
}
