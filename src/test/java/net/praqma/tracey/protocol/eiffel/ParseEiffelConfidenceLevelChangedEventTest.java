package net.praqma.tracey.protocol.eiffel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.cli.Main;
import net.praqma.tracey.protocol.eiffel.events.EiffelConfidenceLevelModifiedEventOuterClass.EiffelConfidenceLevelModifiedEvent;
import net.praqma.tracey.protocol.eiffel.events.EiffelConfidenceLevelModifiedEventOuterClass.EiffelConfidenceLevelModifiedEvent.EiffelConfidenceLevelType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ParseEiffelConfidenceLevelChangedEventTest {

    @Test
    public void parseDefaultWithSuccess() throws Exception {
        String[] args = new String[] { "EiffelConfidenceLevelModifiedEvent",
                "-n", "Cofidence change. Tests for this product passed",
                "-in", "John Doe",
                "-ie", "john.doe@eternity.org",
                "-v", "SUCCESS"
        };

        EiffelArgumentParser eip = new EiffelArgumentParser();
        eip.registerAllParsers();
        EiffelConfidenceLevelModifiedEvent msg = (EiffelConfidenceLevelModifiedEvent)eip.createEvent(args);
        assertEquals(EiffelConfidenceLevelType.SUCCESS, msg.getData().getValue());
    }

    @Test
    public void parseDefaultWithFailure() throws Exception {
        String[] args = new String[] { "EiffelConfidenceLevelModifiedEvent",
                "-n", "Cofidence change. Tests for this product passed",
                "-in", "John Doe",
                "-ie", "john.doe@eternity.org",
                "-v", "FAILURE"
        };

        EiffelArgumentParser eip = new EiffelArgumentParser();
        eip.registerAllParsers();
        EiffelConfidenceLevelModifiedEvent msg = (EiffelConfidenceLevelModifiedEvent)eip.createEvent(args);
        assertEquals(EiffelConfidenceLevelType.FAILURE, msg.getData().getValue());

    }

    @Test
    public void parseDefaultWithInconclusive() throws Exception {
        String[] args = new String[] { "EiffelConfidenceLevelModifiedEvent",
                "-n", "Cofidence change. Tests for this product passed",
                "-in", "John Doe",
                "-ie", "john.doe@eternity.org",
                "-v", "INCONCLUSIVE"
        };

        EiffelArgumentParser eip = new EiffelArgumentParser();
        eip.registerAllParsers();
        EiffelConfidenceLevelModifiedEvent msg = (EiffelConfidenceLevelModifiedEvent)eip.createEvent(args);
        assertEquals(EiffelConfidenceLevelType.INCONCLUSIVE, msg.getData().getValue());
    }

    @Test
    public void parseDefaultWithNoValue() throws Exception {
        String[] args = new String[] { "EiffelConfidenceLevelModifiedEvent",
                "-n", "Cofidence change. Tests for this product passed",
                "-in", "John Doe",
                "-ie", "john.doe@eternity.org",
        };

        EiffelArgumentParser eip = new EiffelArgumentParser();
        eip.registerAllParsers();
        EiffelConfidenceLevelModifiedEvent msg = (EiffelConfidenceLevelModifiedEvent)eip.createEvent(args);
        assertEquals(EiffelConfidenceLevelType.NONE, msg.getData().getValue());
    }

    @Test
    public void testMain() throws Exception {
        File f = new File("testMain_ConfidenceLevel");

        if(f.exists()) {
            f.delete();
        }

        String[] args = new String[] {
                "-f", f.getAbsolutePath(),
                "EiffelConfidenceLevelModifiedEvent",
                "-n", "Cofidence change. Tests for this product passed",
                "-in", "John Doe",
                "-ie", "john.doe@eternity.org",
                "-v", "SUCCESS"
        };
        Main.main(args);
        String contents =  new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())),"UTF-8");
        assertTrue(contents.contains("EiffelConfidenceLevelModifiedEvent"));
        assertTrue(contents.contains("SUCCESS"));
    }
}
