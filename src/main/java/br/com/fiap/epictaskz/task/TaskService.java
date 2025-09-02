package br.com.fiap.epictaskz.task;

import br.com.fiap.epictaskz.config.MessageHelper;
import br.com.fiap.epictaskz.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MessageHelper messageHelper;

    public TaskService(TaskRepository taskRepository, MessageHelper messageHelper) {
        this.taskRepository = taskRepository;
        this.messageHelper = messageHelper;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.delete(getTask(id));
    }

    public void pick(Long id, User user) {
        var task = getTask(id);
        task.setUser(user);
        taskRepository.save(task);
    }

    public void drop(Long id, User user) {
        var task = getTask(id);
        task.setUser(null);
        taskRepository.save(task);
    }

    public void incrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() + 10);
        if(task.getStatus() > 100) task.setStatus(100);
        taskRepository.save(task);
    }

    public void decrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() - 10);
        if(task.getStatus() < 0) task.setStatus(0);
        taskRepository.save(task);
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(messageHelper.get("task.notfound"))
        );
    }
}
