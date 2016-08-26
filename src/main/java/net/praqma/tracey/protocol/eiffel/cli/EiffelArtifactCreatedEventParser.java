package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.Subparser;

public class EiffelArtifactCreatedEventParser {

    public EiffelArtifactCreatedEventParser(final Subparser parser) {

        ArgumentGroup manualGroup = parser.addArgumentGroup("Versioning");
        manualGroup.addArgument("-a", "--artifact-id").dest("aid").help("Artifact id");
        manualGroup.addArgument("-w", "--version").dest("vid").help("Artifact version");
        manualGroup.addArgument("-g", "--group-id").dest("gid").help("Artifact group id");

        ArgumentGroup mavenGroup = parser.addArgumentGroup("Maven versioning");
        mavenGroup.addArgument("-m", "--pom").dest("pom").help("Path to pom file");

        parser.addArgument("-l", "--links").dest("links").nargs("*").help("Add these links to the event");
        parser.addArgument("-c", "--build-command").dest("cmd").setDefault("").help("The command that was used to build the artifact");
    }
}
