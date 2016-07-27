package net.praqma.tracey.protocol.eiffel;

import com.google.protobuf.util.JsonFormat;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.cli.Main;
import net.praqma.tracey.protocol.eiffel.events.EiffelArtifactCreatedEventOuterClass.EiffelArtifactCreatedEvent;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParseEiffelArtifactCreatedEventsTest {

    @Test
    public void parseDefault() throws Exception {
        String[] args = new String[] { "EiffelArtifactCreatedEvent", "-l",
            "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18",
            "-c", "mvn clean package",
            "-a", "artifact-one",
            "-w", "1.0",
            "-g", "artifact-group"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        EiffelArtifactCreatedEvent msg = (EiffelArtifactCreatedEvent)eap.createEvent(args);
        assertEquals("artifact-one", msg.getData().getGav().getArtifactId());
        assertEquals("1.0", msg.getData().getGav().getVersion());
        assertEquals("artifact-group", msg.getData().getGav().getGroupId());
        assertEquals(1, msg.getLinksCount());
        System.out.println(JsonFormat.printer().print(msg));
    }

    @Test
    public void parseFromPom() throws Exception {
        String path = Paths.get(this.getClass().getResource("example-pom.xml").toURI()).toAbsolutePath().toString();
        String[] args = new String[] { "EiffelArtifactCreatedEvent", "-l",
            "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18",
            "-m", path,
            "-c", "mvn clean package"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        EiffelArtifactCreatedEvent msg = (EiffelArtifactCreatedEvent)eap.createEvent(args);
        assertEquals("tracey", msg.getData().getGav().getArtifactId());
        assertEquals("1.0-SNAPSHOT", msg.getData().getGav().getVersion());
        assertEquals("net.praqma", msg.getData().getGav().getGroupId());
        assertEquals(1, msg.getLinksCount());
        System.out.println(JsonFormat.printer().print(msg));
    }

    @Test
    public void testMain() throws Exception {
        String path = Paths.get(this.getClass().getResource("example-pom.xml").toURI()).toAbsolutePath().toString();
        File f = new File("testMain.out");
        String fpath = f.getAbsolutePath();

        String[] args = new String[] {
            "-f",fpath,
            "EiffelArtifactCreatedEvent", "-l",
            "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18",
            "-m", path,
            "-c", "mvn clean package"};
        Main.main(args);
        assertTrue(f.exists());
        String contents =  new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())),"UTF-8");
        assertTrue(contents.contains("EiffelArtifactCreatedEvent"));
    }

    @Test(expected = HelpScreenException.class)
    public void helpScreen() throws Exception {
        String[] args = new String[] { "EiffelArtifactCreatedEvent", "-h" };
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        eap.parseArgs(args);
    }
}
