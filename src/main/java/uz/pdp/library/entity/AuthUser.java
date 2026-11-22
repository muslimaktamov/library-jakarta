package uz.pdp.library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.library.enums.Role;
import uz.pdp.library.enums.Status;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "auth_user")
public class AuthUser extends Auditable {

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, Role role, Status status) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}

