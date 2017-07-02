package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.common.TracerConstant;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.entity.TblTracerFile;
import self.srr.jast.model.entity.TblTracerHistory;
import self.srr.jast.repository.TblTracerFileRepository;
import self.srr.jast.repository.TblTracerHistoryRepository;

import java.util.Date;

/**
 * Tracer service
 * <p>
 * Created by Sharuru on 2017/7/2 0002.
 */
@Service
@Slf4j
public class TracerService {

    @Autowired
    TblTracerFileRepository tblTracerFileRepository;

    @Autowired
    TblTracerHistoryRepository tblTracerHistoryRepository;

    public Boolean isFileInTrack(String filePath) {
        return !(tblTracerFileRepository.findOneByFilePath(filePath) == null);
    }

    public void addFileToTrackQueue(GitFile gitFile) {
        TblTracerFile tblTracerFile = new TblTracerFile();

        tblTracerFile.setFileName(gitFile.getFileName());
        tblTracerFile.setFilePath(gitFile.getFilePath());
        tblTracerFile.setFileStatus(TracerConstant.FILE_STATUS_INITIALIZE);
        tblTracerFile.setReviewStatus(TracerConstant.REVIEW_STATUS_NOT_REVIEWED);
        tblTracerFile.setDeliveryStatus(TracerConstant.DELIVERY_STATUS_WONT_DELIVER);
        tblTracerFile = tblTracerFileRepository.save(tblTracerFile);

        TblTracerHistory tblTracerHistory = new TblTracerHistory();

        tblTracerHistory.setFileId(tblTracerFile.getId());
        tblTracerHistory.setRevisionId(gitFile.getRevisionId());
        tblTracerHistory.setOperationTask(TracerConstant.OPERATION_IMPORT);
        tblTracerHistory.setOperator(TracerConstant.SYSTEM_OPERATOR);
        tblTracerHistory.setOperationTime(new Date());

        tblTracerHistoryRepository.save(tblTracerHistory);
    }
}
