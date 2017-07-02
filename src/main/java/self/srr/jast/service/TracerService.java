package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.jast.model.GitFile;
import self.srr.jast.model.entity.TblTracerFile;
import self.srr.jast.model.entity.TblTracerHistory;
import self.srr.jast.repository.TblTracerFileRepository;
import self.srr.jast.repository.TblTracerHistoryRepository;

import java.time.LocalDateTime;
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

    public void addFiletoTrackQueue(GitFile gitFile, Boolean isFirst) {
        TblTracerFile tblTracerFile = new TblTracerFile();
        tblTracerFile.setFileName(gitFile.getFileName());
        tblTracerFile.setFilePath(gitFile.getFilePath());
        tblTracerFile.setFileStatus("INITIAL_IMPORT");
        tblTracerFile.setReviewStatus("NOT_REVIEWED");
        tblTracerFile.setDeliveryStatus("WON'T_DELIVERIED");
        tblTracerFile = tblTracerFileRepository.save(tblTracerFile);

        TblTracerHistory tblTracerHistory = new TblTracerHistory();
        tblTracerHistory.setFileId(tblTracerFile.getId());
        tblTracerHistory.setRevisionId(gitFile.getRevisionId());
        tblTracerHistory.setOperationTask("INITIAL_IMPORT");
        tblTracerHistory.setOperator(1L);
        tblTracerHistory.setOperationTime(new Date());

        tblTracerHistoryRepository.save(tblTracerHistory);
    }
}
