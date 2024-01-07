package taskmanager.data;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

class TaskFileRepositoryTest {
    private static final String SEED_FILE_PATH="./data/tasks-seed.csv";
    private static final String TEST_FILE_PATH="./data/tasks-test.csv";

    private final TaskFileRepository repository=new TaskFileRepository(TEST_FILE_PATH);

//    known good state
    @BeforeEach
}