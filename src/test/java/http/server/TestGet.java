package http.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;


public class TestGet {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test() throws IOException {
        File created = folder.newFolder("folder");
        File file = folder.newFile("myfile.txt");
        assertTrue(file.exists());
    }
}
