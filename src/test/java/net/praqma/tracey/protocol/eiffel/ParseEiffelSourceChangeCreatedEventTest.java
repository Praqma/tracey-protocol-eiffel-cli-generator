package net.praqma.tracey.protocol.eiffel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.cli.Main;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParseEiffelSourceChangeCreatedEventTest {

    private final File testFile = new File("testMain_SourceChange.json");

    @Test
    public void defaultAcceptance() throws Exception {
        String[] args = new String[] {"-i","mydomain.com", "EiffelSourceChangeCreatedEvent", "-p", "Praqma/Project"};
        EiffelArgumentParser argparser = new EiffelArgumentParser();
        argparser.registerAllParsers();
        EiffelSourceChangeCreatedEvent msg = (EiffelSourceChangeCreatedEvent)argparser.createEvent(args);
        assertEquals("mydomain.com", msg.getMeta().getSource().getDomainId());
    }

    @Test
    public void testMain() throws Exception {
        String[] args = new String[] {
            "-f", testFile.getAbsolutePath(),
            "-i","mydomain.com",
            "EiffelSourceChangeCreatedEvent",
            "-p", "Praqma/Project"};
        Main.main(args);
        assertTrue(testFile.exists());
        String contents =  new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())),"UTF-8");
        assertTrue(contents.contains("EiffelSourceChangeCreatedEvent"));
    }

    @After
    public void doCleanup() throws Exception {
        if(testFile.exists() && ! testFile.delete()) {
            throw new IOException("Can't delete " + testFile.getAbsolutePath());
        }
    }

}
