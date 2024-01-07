package taskmanager.data;
import taskmanager.models.Task;
import java.util.List;

public interface TaskRepository {
//    CRUD
    List<Task> findAll() throws DataAccessException;
    Task findById(int taskId) throws DataAccessException;
    Task create(Task task);
    boolean update(Task task);
    boolean delete(int taskId);
}
