package ch.glauser.gestionstock.common.view;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

/**
 * Common entity
 */
@Getter
@NoArgsConstructor
@Immutable
@MappedSuperclass
public abstract class View {
    @Id
    @Column(name = "ID",
            nullable = false,
            updatable = false,
            unique = true)
    private Long id;

    @Column(name = "CREATION_USER", updatable = false)
    private String creationUser;

    @Column(name = "CREATION_DATE", updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "MODIFICATION_USER", updatable = false)
    private String modificationUser;

    @Column(name = "MODIFICATION_DATE", updatable = false)
    private LocalDateTime modificationDate;
}
