package crm.system.springTask.__1.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class ApplicationRequest {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(nullable = false, length = 1000)
    private String commentary;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private boolean handled; //обработано или нет
}