package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.Subparser;

public class EiffelArtifactCreatedEventParser {

    private final Subparser parser;

    public EiffelArtifactCreatedEventParser(final Subparser parser) {
        this.parser = parser;
        parser.addArgument("-a", "--artifact-id").dest("aid");
        parser.addArgument("-w", "--version").dest("vid");
        parser.addArgument("-g", "--group-id").dest("gid");
    }
}
