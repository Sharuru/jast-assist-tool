package self.srr.jast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.jast.model.entity.TblAstFile;

/**
 * File repository
 */
@Repository
public interface TblAstFileRepository extends JpaRepository<TblAstFile, Long>, QueryByExampleExecutor<TblAstFile> {

    TblAstFile findOneByFilePath(String filePath);

}
