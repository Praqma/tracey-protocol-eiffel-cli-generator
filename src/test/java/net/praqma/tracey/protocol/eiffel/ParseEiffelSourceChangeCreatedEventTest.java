package net.praqma.tracey.protocol.eiffel;

import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ParseEiffelSourceChangeCreatedEventTest {

    @Test
    public void defaultAcceptance() throws Exception {
        String[] args = new String[] {"-i","mydomain.com", "EiffelSourceChangeCreatedEvent", "-p", "Praqma/Project"};
        EiffelArgumentParser argparser = new EiffelArgumentParser();
        argparser.registerAllParsers();
        EiffelSourceChangeCreatedEvent msg = (EiffelSourceChangeCreatedEvent)argparser.creteEvent(args);
        assertEquals("mydomain.com", msg.getMeta().getSource().getDomainId());
    }

    @Test
    public void testHelp() {
        String[] args = new String[] { "EiffelCompositionDefinedEvent", "-h"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        assertNotNull(eap.parseArgs(args));
    }
}
