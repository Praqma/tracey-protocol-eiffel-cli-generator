package net.praqma.tracey.protocol.eiffel;

import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.models.Models;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by mads on 10/4/17.
 */
public class EiffelArtifactReusedEventParserTest {

    @Test
    public void testGetLinkFromPublishedArtifact() throws IOException {
        String file = this.getClass().getResource("ArtP.json").getFile();
        Models.Link linkTypeToCreate = EiffelArgumentParser.linkFromLink(file, Models.Link.LinkType.ARTIFACT, Models.Link.LinkType.REUSED_ARTIFACT);
        assertEquals("b1cd0c8b-bcc7-4c09-9e7c-c722b2d1b145", linkTypeToCreate.getId());
        assertEquals(Models.Link.LinkType.REUSED_ARTIFACT, linkTypeToCreate.getType());
    }
}
