package uz.pdp.library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "upload")
public class Upload extends Auditable {

    @Column(nullable = false)
    private String generatedName;

    @Column(nullable = false)
    private String originName;

    private long size;

    @Column(nullable = false)
    private String extension;

    @Builder(builderMethodName = "childBuilder")
    public Upload(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String generatedName, String originName, long size, String extension) {
        super(id, createdAt, updatedAt);
        this.generatedName = generatedName;
        this.originName = originName;
        this.size = size;
        this.extension = extension;
    }
}

