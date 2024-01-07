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


}