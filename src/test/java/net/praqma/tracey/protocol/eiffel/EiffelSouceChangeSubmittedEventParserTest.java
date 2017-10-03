package net.praqma.tracey.protocol.eiffel;

import net.praqma.tracey.protocol.eiffel.cli.EiffelSourceChangeSubmittedEventParser;
import net.praqma.tracey.protocol.eiffel.models.Models;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by mads on 10/3/17.
 */
public class EiffelSouceChangeSubmittedEventParserTest {

    @Test
    public void testStaticParsingFunction() throws IOException {
        String file = this.getClass().getResource("SCC.json").getFile();
        System.out.println("FILE == "+file);
        Models.Link link = EiffelSourceChangeSubmittedEventParser.changeLinkFromJson(file);
        assertEquals(Models.Link.LinkType.CHANGE, link.getType());
        assertEquals("c110a837-e8ff-4d5a-8189-e16328f0d24d", link.getId());
    }
}
