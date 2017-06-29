package self.srr.jast.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Role entity
 * <p>
 * Created by Sharuru on 2017/06/29.
 */
@Data
@Entity
@Table(name = "tracer_role")
@EntityListeners(AuditingEntityListener.class)
public class TblTracerRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "tracer_user_id_seq")
    private Long id;

    private String username;

    @Column(name = "role_name")
    private String rollName;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblTracerRole() {
    }
}
