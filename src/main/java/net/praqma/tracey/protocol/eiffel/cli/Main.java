package net.praqma.tracey.protocol.eiffel.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import com.jcabi.manifests.Manifests;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent;
import net.praqma.tracey.protocol.eiffel.factories.EiffelSourceChangeCreatedEventFactory;
import net.praqma.tracey.protocol.eiffel.models.Models.Link;
import net.praqma.tracey.protocol.eiffel.models.Models.Data.GAV;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.util.JsonFormat;
import net.praqma.utils.parsers.cmg.api.CommitMessageParser;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.*;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    // TODO: create enum with tracker types?
    private static final List<String> supportedParsers = Arrays.asList("GitHub", "Jira");
    private static final String name = "Eiffel command line generator";
    private static final String uri = "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator";

    public static void main (String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // TODO: move CLI arguments to a separate class
        ArgumentParser parser = ArgumentParsers.newArgumentParser("generator")
                .defaultHelp(true)
                .description("Generate Eiffel messages");
        // Add global options
        parser.addArgument("-f", "--file").dest("file").help("Path to the file to save generated message");
        parser.addArgument("-d", "--debug").dest("debug").action(Arguments.storeTrue()).setDefault(false).help("Output debug logs");
        parser.addArgument("-i", "--domainId").dest("domainId").help("DomainId to use in the message").setDefault("");

        Subparsers subparsers = parser.addSubparsers();
        // Options per positional argument
        Subparser eiffelSourceChangeCreatedEvent = subparsers.addParser("EiffelSourceChangeCreatedEvent");
        eiffelSourceChangeCreatedEvent.addArgument("-t", "--tracker")
                .dest("tracker")
                .help("Type of issue tracking system to use when generation URL for issues parsed from the commit message. Assuming GitHub if not set.")
                .setDefault("GitHub")
                .choices(supportedParsers);
        eiffelSourceChangeCreatedEvent.addArgument("-u", "--url")
                .dest("url")
                .help("URL of issue tracker. Will be used for parsed issues URL generation. Assume http://github.com if not set")
                .setDefault("http://github.com");
        eiffelSourceChangeCreatedEvent.addArgument("-p", "--project")
                .dest("project")
                .help("Project that issue belongs to. Will be used for parsed issues URL generation.");
        eiffelSourceChangeCreatedEvent.addArgument("-r", "--repo")
                .dest("repo")
                .help("Path to repo that contains commit you want to parse. Assuming current directory if not set")
                .setDefault(".");
        eiffelSourceChangeCreatedEvent.addArgument("-c", "--commitId")
                .dest("commit")
                .help("sha1 to parse and generate message for. Assuming HEAD if not set")
                .setDefault("HEAD");
        eiffelSourceChangeCreatedEvent.addArgument("-b", "--branch")
                .dest("branch")
                .help("branch name to specify in the message - we can't guess branch automatically since commit might belong to multiple branches. Assuming master if not set")
                .setDefault("master");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        if (ns.getBoolean("debug") == true) {
            Logger rootLog = Logger.getLogger("");
            rootLog.setLevel( Level.FINE );
            rootLog.getHandlers()[0].setLevel( Level.FINE );
        }


        GAV gav = GAV.newBuilder()
                .setGroupId(Manifests.read("Implementation-Vendor"))
                .setArtifactId(Manifests.read("Implementation-Title"))
                .setVersion(Manifests.read("Implementation-Version"))
                .build();

        // TODO: Create a separate class per positional argument
        final EiffelSourceChangeCreatedEventFactory factory = new EiffelSourceChangeCreatedEventFactory(getHostName(),
                name,
                uri,
                ns.getString("domainId"),
                gav);
        CommitMessageParser cmgParser = null;
        if (supportedParsers.contains(ns.getString("tracker"))) {
            Class<?> parserClass = Class.forName("net.praqma.utils.parsers.cmg.impl." + ns.getString("tracker"));
            Constructor<?> constructor = parserClass.getConstructor(URL.class, String.class);
            try {
                cmgParser = (CommitMessageParser) constructor.newInstance(new URL(ns.getString("url")), ns.getString("project"));
            } catch (InstantiationException instantiationException) {
                log.severe("Internal error! Can't instantiate commit message parser\n" + instantiationException);
                System.exit(1);
            }
        } else {
            log.severe("Tracker type " + ns.getString("tracker") + " not supported. Supported types are " + supportedParsers.toString());
            System.exit(1);
        }
        final List<Link> links = new ArrayList<>();
        links.add(Link.newBuilder().setType(Link.LinkType.PREVIOUS_VERSION).setId(UUID.randomUUID().toString()).build());
        links.add(Link.newBuilder().setType(Link.LinkType.CAUSE).setId(UUID.randomUUID().toString()).build());
        log.fine(cmgParser.getClass().toString());
        factory.parseFromGit(Paths.get(ns.getString("repo")).toAbsolutePath().normalize().toString(),
                ns.getString("commit"),
                ns.getString("branch"),
                cmgParser);
        final EiffelSourceChangeCreatedEvent.Builder event = (EiffelSourceChangeCreatedEvent.Builder) factory.create();
        event.addAllLinks(links);

        if (ns.getString("file") != null) {
            File f = new File(ns.getString("file"));

            if(f.exists()) {
                f.delete();
            }

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ns.getString("file")), "utf-8"))) {
                writer.write(JsonFormat.printer().print(event));
            }
        } else {
            log.info(JsonFormat.printer().print(event));
        }
    }

    private static String getHostName() {
        String hostname = "Unknown";
        try
        {
            hostname = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            log.warning("Hostname can not be resolved due to the following. Use " + hostname + " as a hostname\n" + e.getMessage());
        }
        log.fine("Retunr hostname: " + hostname);
        return hostname;
    }
}
