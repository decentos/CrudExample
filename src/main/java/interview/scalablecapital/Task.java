package interview.scalablecapital;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private TaskStatus status = TaskStatus.CREATED;

    public Task(String title) {
        this.title = title;
    }

    private Task() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public TaskDto toDto() {
        return new TaskDto(String.valueOf(id), title, description, status.name());
    }
}
