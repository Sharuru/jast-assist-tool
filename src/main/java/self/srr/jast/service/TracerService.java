package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.common.AstConstant;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.entity.TblAstFile;
import self.srr.jast.model.entity.TblAstHistory;
import self.srr.jast.repository.TblAstFileRepository;
import self.srr.jast.repository.TblAstHistoryRepository;

import java.util.Date;

/**
 * Tracer service
 */
@Service
@Slf4j
public class TracerService {

    @Autowired
    TblAstFileRepository tblAstFileRepository;

    @Autowired
    TblAstHistoryRepository tblAstHistoryRepository;

    public Boolean isFileInTrack(String filePath) {
        return !(tblAstFileRepository.findOneByFilePath(filePath) == null);
    }

    public void addFileToTrackQueue(GitFile gitFile) {
        TblAstFile tblAstFile = new TblAstFile();

        tblAstFile.setFileName(gitFile.getFileName());
        tblAstFile.setFilePath(gitFile.getFilePath());
        tblAstFile.setRevisionId(gitFile.getRevisionId());
        tblAstFile.setFileStatus(AstConstant.DEL_FILE_STATUS_INITIALIZE);
        tblAstFile.setReviewStatus(AstConstant.DEL_REVIEW_STATUS_NOT_REVIEWED);
        tblAstFile.setDeliveryStatus(AstConstant.DEL_DELIVERY_STATUS_WONT_DELIVER);
        tblAstFile = tblAstFileRepository.save(tblAstFile);

        TblAstHistory tblAstHistory = new TblAstHistory();

        tblAstHistory.setFileId(tblAstFile.getId());
        tblAstHistory.setRevisionId(gitFile.getRevisionId());
        tblAstHistory.setOperationTask(AstConstant.DEL_OPERATION_IMPORT);
        tblAstHistory.setOperator(AstConstant.DEL_SYSTEM_OPERATOR);
        tblAstHistory.setOperationTime(new Date());

        tblAstHistoryRepository.save(tblAstHistory);
    }
}
