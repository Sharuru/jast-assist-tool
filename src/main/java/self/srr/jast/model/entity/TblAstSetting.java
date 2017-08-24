package self.srr.jast.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Setting entity
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Data
@Entity
@Table(name = "ast_setting")
@EntityListeners(AuditingEntityListener.class)
public class TblAstSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "ast_setting_id_seq")
    private Long id;

    @Column(name = "setting_group")
    private String settingGroup;

    private String settings;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblAstSetting() {

    }
}
