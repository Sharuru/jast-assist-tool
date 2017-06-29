package self.srr.jast.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * User entity
 * <p>
 * Created by Sharuru on 2017/06/29.
 */
@Data
@Entity
@Table(name = "tracer_user")
@EntityListeners(AuditingEntityListener.class)
public class TblTracerUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "tracer_user_id_seq")
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String email;

    private Boolean enable;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Set<TblTracerRole> roles = new HashSet<>();

    public TblTracerUser() {
    }
}
