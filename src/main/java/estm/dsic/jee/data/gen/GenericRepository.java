package estm.dsic.jee.data.gen;

import java.util.List;

public interface GenericRepository<T, I> {
    T findById(I id);
    List<T> findAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean deleteById(I id);
    List<T> searchByKeyword(String keyword);
}

