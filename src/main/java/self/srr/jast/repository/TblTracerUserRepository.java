package self.srr.jast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.jast.model.entity.TblTracerUser;

/**
 * User repository
 * <p>
 * Created by Sharuru on 2017/06/29.
 */
@Repository
public interface TblTracerUserRepository extends JpaRepository<TblTracerUser, Long>, QueryByExampleExecutor<TblTracerUser> {

    TblTracerUser findOneByUsername(String username);
}
