package net.praqma.tracey.protocol.eiffel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.cli.Main;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParseEiffelSourceChangeCreatedEventTest {

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
        File f = new File("testMain_SourceChange.out");

        if(f.exists()) {
            f.delete();
        }
        
        String[] args = new String[] {
            "-f", f.getAbsolutePath(),
            "-i","mydomain.com",
            "EiffelSourceChangeCreatedEvent",
            "-p", "Praqma/Project"};
        Main.main(args);
        assertTrue(f.exists());
        String contents =  new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())),"UTF-8");
        assertTrue(contents.contains("EiffelSourceChangeCreatedEvent"));
    }

}
