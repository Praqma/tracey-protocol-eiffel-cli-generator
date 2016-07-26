package net.praqma.tracey.protocol.eiffel;

import com.google.protobuf.util.JsonFormat;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.events.EiffelArtifactCreatedEventOuterClass.EiffelArtifactCreatedEvent;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
}
