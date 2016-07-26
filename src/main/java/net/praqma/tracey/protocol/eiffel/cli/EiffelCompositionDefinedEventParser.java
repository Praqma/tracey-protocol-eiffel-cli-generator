package net.praqma.tracey.protocol.eiffel.cli;

import net.sourceforge.argparse4j.inf.Subparser;

public class EiffelCompositionDefinedEventParser {
    public EiffelCompositionDefinedEventParser(Subparser parser) {
        parser.addArgument("-n", "--name").dest("name").setDefault("Compsition name");
        parser.addArgument("-l", "--links").dest("links").nargs("*").help("Links to attach to this event. Use form <TYPE>:<UUID>");
    }
}
