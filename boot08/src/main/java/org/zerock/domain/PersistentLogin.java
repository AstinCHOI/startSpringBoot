package org.zerock.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name="persistent_logins")
@EqualsAndHashCode(of="series")
public class PersistentLogin {
    @Id
    private String series;

    private String username;
    private String token;

    @UpdateTimestamp
    private LocalDateTime lastUsed;
}
