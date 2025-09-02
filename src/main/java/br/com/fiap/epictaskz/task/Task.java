package br.com.fiap.epictaskz.task;

import br.com.fiap.epictaskz.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{task.title.notblank}")
    private String title;

    @Size(min = 20, max = 255, message = "{task.description.size}")
    private String description;

    @Min(value = 1, message = "{task.score.min}")
    @Max(value = 100, message = "{task.score.max}")
    private int score;

    @Min(value = 0, message = "{task.status.min}")
    @Max(value = 100, message = "{task.status.max}")
    private int status;

    @ManyToOne
    private User user;

}
