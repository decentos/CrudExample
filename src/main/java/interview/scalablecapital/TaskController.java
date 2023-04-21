package interview.scalablecapital;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private final TaskRepository repository;

    @Autowired
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<Long> createTask(@RequestBody TaskDto dto) {
        try {
            Task entity = new Task(dto.getTitle());
            entity.setDescription(dto.getDescription());
            Task saved = repository.save(entity);
            return ResponseEntity.ok(saved.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") long id) {
        try {
            Optional<Task> task = repository.findById(id);
            return task.map(value -> ResponseEntity.ok(value.toDto()))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable("id") long id,
            @RequestBody TaskDto dto
    ) {
        try {
            if (dto.getStatus() != null && !EnumUtils.isValidEnum(TaskStatus.class, dto.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Available statuses are: CREATED, APPROVED, REJECTED, BLOCKED, DONE.");
            }

            Optional<Task> task = repository.findById(id);
            if (task.isPresent()) {
                if (dto.getTitle() != null) task.get().setTitle(dto.getTitle());
                if (dto.getDescription() != null) task.get().setDescription(dto.getDescription());
                if (dto.getStatus() != null) task.get().setTaskStatus(TaskStatus.valueOf(dto.getStatus()));
                repository.save(task.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id) {
        try {
            Optional<Task> task = repository.findById(id);
            if (task.isPresent()) {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        try {
            List<TaskDto> tasks = new ArrayList<>();
            repository.findAll().forEach(it -> tasks.add(it.toDto()));
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
