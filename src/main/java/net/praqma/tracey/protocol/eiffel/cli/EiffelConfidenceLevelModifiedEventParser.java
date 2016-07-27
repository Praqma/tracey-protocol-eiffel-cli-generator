package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.Subparser;

public class EiffelConfidenceLevelModifiedEventParser {

    public EiffelConfidenceLevelModifiedEventParser(Subparser parser) {
        parser.addArgument("-n", "--name").dest("name").setDefault("").help("Name of this confidence level");
        parser.addArgument("-v", "--value").dest("value").choices(
                "FAILURE",
                "INCONCLUSIVE",
                "SUCCESS",
                "NONE"

        ).setDefault("NONE");
        parser.addArgument("-in", "--issuer-name").dest("iName").required(true);
        parser.addArgument("-ie", "--issuer-email").dest("iEmail").required(true);
        parser.addArgument("-ii", "--issuer-id").dest("iId");
        parser.addArgument("-ig", "--issuer-group").dest("iGroup");
        parser.addArgument("-l", "--links").dest("links");
    }
}
