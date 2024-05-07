package ma.m2t.paywidget.model;


import jakarta.persistence.*;
import lombok.Data;
import ma.m2t.paywidget.enums.ERole;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}
