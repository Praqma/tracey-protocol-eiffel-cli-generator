package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.Subparser;

/**
 * Created by mads on 10/4/17.
 */
public class EiffelArtifactPublishedEventParser {
    public EiffelArtifactPublishedEventParser(Subparser parser) {
        parser.addArgument("-l", "--links").dest("links").nargs("*").help("Add these links to the event");
        parser.addArgument("-k", "--location").dest("location").nargs("*").help("Specify location of the artifact published");
        parser.addArgument("-j", "--json").dest("json").help("Pass a target json file path this in to add the ArtC meta id as link ARTIFACT");
    }
}
