package self.srr.jast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.jast.model.entity.TblTracerHistory;
import self.srr.jast.model.entity.TblTracerUser;

/**
 * History repository
 * <p>
 * Created by Sharuru on 2017/7/2 0002.
 */
@Repository
public interface TblTracerHistoryRepository extends JpaRepository<TblTracerHistory, Long>, QueryByExampleExecutor<TblTracerHistory> {
}
