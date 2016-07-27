package net.praqma.tracey.protocol.eiffel.cli;

import java.util.Arrays;
import java.util.List;
import net.sourceforge.argparse4j.inf.Subparser;
import org.apache.log4j.Logger;

public class EiffelSourceChangeCreatedParser {

    private static final Logger LOG = Logger.getLogger(EiffelSourceChangeCreatedParser.class.getName());
    private static final List<String> SUPPORTEDPARSERS = Arrays.asList("GitHub", "Jira");

    public EiffelSourceChangeCreatedParser(final Subparser parser) {
        parser.addArgument("-t", "--tracker")
                .dest("tracker")
                .help("Type of issue tracking system to use when generation URL for issues parsed from the commit message. Assuming GitHub if not set.")
                .setDefault("GitHub")
                .choices(SUPPORTEDPARSERS);
        parser.addArgument("-u", "--url")
                .dest("url")
                .help("URL of issue tracker. Will be used for parsed issues URL generation. Assume http://github.com if not set")
                .setDefault("http://github.com");
        parser.addArgument("-p", "--project")
                .dest("project")
                .help("Project that issue belongs to. Will be used for parsed issues URL generation.");
        parser.addArgument("-r", "--repo")
                .dest("repo")
                .help("Path to repo that contains commit you want to parse. Assuming current directory if not set")
                .setDefault(".");
        parser.addArgument("-c", "--commitId")
                .dest("commit")
                .help("sha1 to parse and generate message for. Assuming HEAD if not set")
                .setDefault("HEAD");
        parser.addArgument("-b", "--branch")
                .dest("branch")
                .help("branch name to specify in the message - we can't guess branch automatically since commit might belong to multiple branches. Assuming master if not set")
                .setDefault("master");
        parser.addArgument("-l", "--links")
                .dest("links")
                .help("Spicify links to add to this event");
    }

    public boolean supports(String parser) {
        if(!SUPPORTEDPARSERS.contains(parser))
            LOG.warn("Tracker type " + parser + " not supported. Supported types are " + SUPPORTEDPARSERS.toString());
        return SUPPORTEDPARSERS.contains(parser);
    }

}
