package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.Subparser;

public class EiffelArtifactCreatedEventParser {

    public EiffelArtifactCreatedEventParser(final Subparser parser) {

        ArgumentGroup manualGroup = parser.addArgumentGroup("Versioning");
        manualGroup.addArgument("-a", "--artifact-id").dest("aid");
        manualGroup.addArgument("-w", "--version").dest("vid");
        manualGroup.addArgument("-g", "--group-id").dest("gid");

        ArgumentGroup mavenGroup = parser.addArgumentGroup("Maven versioning");
        manualGroup.addArgument("-m", "--pom").dest("pom").help("Path to pom file");

        parser.addArgument("-l", "--links").dest("links").nargs("*");
        parser.addArgument("-c", "--build-command").dest("cmd");
    }
}
