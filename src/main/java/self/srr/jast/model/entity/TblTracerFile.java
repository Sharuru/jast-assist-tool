package self.srr.jast.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * File entity
 * <p>
 * Created by Sharuru on 2017/7/2 0002.
 */
@Data
@Entity
@Table(name = "tracer_file")
@EntityListeners(AuditingEntityListener.class)
public class TblTracerFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "tracer_file_id_seq")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "revision_id")
    private String revisionId;

    private String keyword1;

    private String keyword2;

    @Column(name = "file_status")
    private String fileStatus;

    @Column(name = "review_status")
    private String reviewStatus;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblTracerFile() {

    }
}
