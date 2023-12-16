package tiktzuki.e_store.infrastructure.repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    /**
     * Only for @Id
     */
    Optional<T> findById(ID id);

    List<T> findAll();

    /**
     * Only for @Id
     */
    List<T> findAllById(Iterable<? extends ID> ids);

    List<T> extract(ResultSet rs);

    int insert(T entity);

    int insertAll(Iterable<T> entities);

    int update(T entity);

    int updateAll(Iterable<T> entities);

    /**
     * Only for @Id
     */
    void deleteById(ID id);

    /**
     * Only for @Id
     */
    void deleteAllById(Iterable<? extends ID> ids);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    long count();
}
