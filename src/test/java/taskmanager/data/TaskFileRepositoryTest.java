package taskmanager.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.models.Status;
import taskmanager.models.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

import java.util.List;

class TaskFileRepositoryTest {
    private static final String SEED_FILE_PATH="./data/tasks-seed.csv";
    private static final String TEST_FILE_PATH="./data/tasks-test.csv";

    private final TaskFileRepository repository=new TaskFileRepository(TEST_FILE_PATH);

//    known good state
    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath=Paths.get(SEED_FILE_PATH);
        Path testPath=Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath,testPath,StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void shouldFindAll() throws DataAccessException{
        List<Task> actual=repository.findAll();
        assertEquals(2,actual.size());

        Task task=actual.get(0);
        assertEquals(1,task.getId());
        assertEquals("2024-01-05",task.getCreatedOn());
        assertEquals("LeetCode",task.getTitle());
        assertEquals("Solve 5 LeetCode problems",task.getDescription());
        assertEquals("2024-01-05",task.getDueDate());
        assertEquals(Status.COMPLETE, task.getStatus());

    }

    @Test
    public void shouldFindByExistingId() throws DataAccessException{
        Task taskOne=repository.findById(1);
        assertNotNull(taskOne);
        assertEquals(1,taskOne.getId());
        assertEquals("2024-01-05",taskOne.getCreatedOn());
        assertEquals("LeetCode",taskOne.getTitle());
        assertEquals("Solve 5 LeetCode problems",taskOne.getDescription());
        assertEquals("2024-01-05",taskOne.getDueDate());
        assertEquals(Status.COMPLETE, taskOne.getStatus());
    }


    @Test
    public void shoudNotFindNonExistingId() throws DataAccessException{
        Task notValid = repository.findById(1000);
        assertNull(notValid);
    }

    @Test
    public void shouldCreate() throws DataAccessException{
        Task task=new Task(
                0,
                "2024-01-06",
                "Get a job!",
                "Please give me a job",
                "2024-05-01",
                Status.TODO


        );

        Task actual=repository.create(task);
        assertEquals(3, actual.getId());

        List<Task> tasks = repository.findAll();
        assertEquals(3,tasks.size());
        assertEquals("2024-01-06",actual.getCreatedOn());
        assertEquals("Get a job!",actual.getTitle());
        assertEquals("Please give me a job",actual.getDescription());
        assertEquals("2024-05-01",actual.getDueDate());
        assertEquals(Status.TODO,actual.getStatus());

    }

    @Test
    public void shouldCreateWithCommas() throws DataAccessException{
        Task task=new Task(
                0,
                "2024-01-06",
                "I will get a job!",
                "I will become a software engineer",
                "2024-05-01",
                Status.TODO


        );

        Task actual=repository.create(task);
        assertEquals(3, actual.getId());

        List<Task> tasks = repository.findAll();
        assertEquals(3,tasks.size());
        assertEquals("2024-01-06",actual.getCreatedOn());
        assertEquals("I will get a job!",actual.getTitle());
        assertEquals("I will become a software engineer",actual.getDescription());
        assertEquals("2024-05-01",actual.getDueDate());
        assertEquals(Status.TODO,actual.getStatus());

    }

    @Test
    public void shouldUpdate() throws DataAccessException{
        Task task=repository.findById(1);
        task.setStatus(Status.IN_PROGRESS);
        task.setDescription("Solve 10 LeetCode problems");

        boolean res=repository.update(task);
        assertTrue(res);

        assertNotNull(task);

        assertEquals(1,task.getId());
        assertEquals("2024-01-05",task.getCreatedOn());
        assertEquals("LeetCode",task.getTitle());
        assertEquals("Solve 10 LeetCode problems",task.getDescription());
        assertEquals("2024-01-05",task.getDueDate());
        assertEquals(Status.IN_PROGRESS, task.getStatus());

    }

    @Test
    public void shouldNotUpdateWithNonExistingId() throws DataAccessException{
        Task task=new Task(10000000,"","","","",Status.COMPLETE);
        boolean res = repository.update(task);
        assertFalse(res);
    }

    @Test
    public void shouldDelete() throws DataAccessException{
        boolean res=repository.delete(1);
        assertTrue(res);

        List<Task> tasks=repository.findAll();
        assertEquals(1,tasks.size());

        Task task=repository.findById(1);
        assertNull(task);
    }

    @Test
    public void shouldNotDeleteUnknownId() throws DataAccessException{
        boolean res=repository.delete(100000);
        assertFalse(res);
    }
}