package net.praqma.tracey.protocol.eiffel;

import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.events.EiffelCompositionDefinedEventOuterClass.EiffelCompositionDefinedEvent;
import net.praqma.tracey.protocol.eiffel.models.Models.Link;
import net.sourceforge.argparse4j.inf.Namespace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class ParseEiffelCompositionDefinedEventTest {

    @Test
    public void testCompositonEventCreateCommand() throws Exception {
       String[] args = new String[] {"EiffelCompositionDefinedEvent", "-l", "PREVIOUS_VERSION:8a718a03-f473-4e61-9bae-e986885fee18", "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18"};
       EiffelArgumentParser eap = new EiffelArgumentParser();
       Link l1 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.CAUSE).build();
       Link l2 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.PREVIOUS_VERSION).build();
       eap.registerAllParsers();
       Namespace ns = eap.parseArgs(args);
       assertNotNull(ns.getList("links"));
       EiffelCompositionDefinedEvent msg = (EiffelCompositionDefinedEvent)eap.creteEvent(args);
       assertEquals("Composition name", msg.getData().getName());
       assertThat(msg.getLinksList(), hasItems(l1, l2));
    }

    @Test
    public void testHelp() {
        String[] args = new String[] { "EiffelCompositionDefinedEvent", "-h"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        assertNotNull(eap.parseArgs(args));
    }

}
