package self.srr.jast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.jast.model.entity.TblAstUser;

/**
 * User repository
 */
@Repository
public interface TblAstUserRepository extends JpaRepository<TblAstUser, Long>, QueryByExampleExecutor<TblAstUser> {

    TblAstUser findOneByUsername(String username);
}
