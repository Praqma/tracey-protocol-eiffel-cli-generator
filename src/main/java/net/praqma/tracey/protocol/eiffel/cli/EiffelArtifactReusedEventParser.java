package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.Subparser;

/**
 * Created by mads on 10/4/17.
 */
public class EiffelArtifactReusedEventParser {
    public EiffelArtifactReusedEventParser(Subparser parser) {
        parser.addArgument("-l", "--links").dest("links").nargs("*").help("Links to attach to this event. Use form <TYPE>:<UUID>");
        parser.addArgument("-n", "--from-published").dest("fromPublished").help("Point to a file with a EiffelArtifactPublishedEvent");
        parser.addArgument("-c", "--from-composition").dest("fromComposition").help("Point to a file with a EiffelCompositionDefinedEvent");
    }
}
