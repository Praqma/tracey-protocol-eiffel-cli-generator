/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.tracey.protocol.eiffel.cli;

import com.google.protobuf.GeneratedMessage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass;
import net.praqma.tracey.protocol.eiffel.events.EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent;
import net.praqma.tracey.protocol.eiffel.factories.BaseFactory;
import net.praqma.tracey.protocol.eiffel.factories.EiffelArtifactCreatedEventFactory;
import net.praqma.tracey.protocol.eiffel.factories.EiffelCompositionDefinedEventFactory;
import net.praqma.tracey.protocol.eiffel.factories.EiffelSourceChangeCreatedEventFactory;
import net.praqma.tracey.protocol.eiffel.models.Models;
import net.praqma.tracey.protocol.eiffel.models.Models.Link;
import net.praqma.utils.parsers.cmg.api.CommitMessageParser;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparsers;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EiffelArgumentParser {
    private static final Logger LOG = Logger.getLogger(EiffelArgumentParser.class.getName());


    private static final String NAME = "Eiffel command line generator";
    private static final String URI = "https://github.com/Praqma/tracey-protocol-eiffel-cli-generator";

    private ArgumentParser main;
    private Subparsers subParsers;
    public HashMap<Class, Object> parsers = new HashMap<>();

    public EiffelArgumentParser() {
        // To avoid log4j printouts when the program starts
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.WARN);

        // TODO: move CLI arguments to a separate class
        main = ArgumentParsers.newArgumentParser("generator")
                .defaultHelp(true)
                .description("Generate Eiffel messages");
        // Add global options
        main.addArgument("-f", "--file").dest("file").help("Path to the file to save generated message");
        main.addArgument("-d", "--debug").dest("debug").action(Arguments.storeTrue()).setDefault(false).help("Output debug logs");
        main.addArgument("-i", "--domainId").dest("domainId").help("DomainId to use in the message").setDefault("");
        this.subParsers = main.addSubparsers();
    }

    public Namespace parseArgs(String[] args) {
        try {
            Namespace ns = main.parseArgs(args);
            return ns;
        } catch (ArgumentParserException ex) {
            main.handleError(ex);
            ex.printStackTrace(System.out);
        }
        return null;
    }

    /**
     * @return the subParsers
     */
    public Subparsers getSubParsers() {
        return subParsers;
    }

    /**
     * TODO: Not very flexible ATM.
     */
    public void registerAllParsers() {
        EiffelArtifactCreatedEventParser artifacts = new EiffelArtifactCreatedEventParser(getSubParsers().addParser("EiffelArtifactCreatedEvent"));
        parsers.put(artifacts.getClass(), artifacts);

        EiffelSourceChangeCreatedParser sourcechange = new EiffelSourceChangeCreatedParser(getSubParsers().addParser("EiffelSourceChangeCreatedEvent"));
        parsers.put(sourcechange.getClass(), sourcechange);

        EiffelCompositionDefinedEventParser composition = new EiffelCompositionDefinedEventParser(getSubParsers().addParser("EiffelCompositionDefinedEvent"));
        parsers.put(composition.getClass(), composition);
    }

    public <T> T getParser(Class<T> t) {
        return (T)parsers.get(t);
    }

    //TODO: Move these to seperate class
    public GeneratedMessage creteEvent(String[] args) throws Exception {
        Namespace ns = parseArgs(args);
        List<String> argList = Arrays.asList(args);

        if(argList.contains("EiffelArtifactCreatedEvent")) {
            EiffelArtifactCreatedEventFactory artifactCreatedEventFactory = new EiffelArtifactCreatedEventFactory(NAME, URI, ns.getString("domainId"));
            artifactCreatedEventFactory.setBuildCommand(ns.getString("cmd"));
            extractLinks(ns, artifactCreatedEventFactory);
            if(ns.getString("pom") != null) {
                artifactCreatedEventFactory.parseFromPom(ns.getString("pom"));
            } else {
                artifactCreatedEventFactory.setGav(ns.getString("gid"), ns.getString("aid"), ns.getString("vid"));
            }

            return (GeneratedMessage) artifactCreatedEventFactory.create().build();

        } else if(argList.contains("EiffelCompositionDefinedEvent")) {
            EiffelCompositionDefinedEventFactory compositionDefinedEventFactory = new EiffelCompositionDefinedEventFactory(NAME, URI, ns.getString("domainId"));
            compositionDefinedEventFactory.setName(ns.getString("name"));
            extractLinks(ns, compositionDefinedEventFactory);
            return (GeneratedMessage) compositionDefinedEventFactory.create().build();
        } else if(argList.contains("EiffelSourceChangeCreatedEvent")) {
            EiffelSourceChangeCreatedEventFactory sourceChangeCreatedEventFactory = new EiffelSourceChangeCreatedEventFactory(NAME, URI, ns.getString("domainId"));
            CommitMessageParser cmgParser = null;
            if (getParser(EiffelSourceChangeCreatedParser.class).supports(ns.getString("tracker"))) {
                Class<?> parserClass = Class.forName("net.praqma.utils.parsers.cmg.impl." + ns.getString("tracker"));
                Constructor<?> constructor = parserClass.getConstructor(URL.class, String.class);
                cmgParser = (CommitMessageParser) constructor.newInstance(new URL(ns.getString("url")), ns.getString("project"));
                List<Models.Link> links = new ArrayList<>();
                links.add(Models.Link.newBuilder().setType(Models.Link.LinkType.PREVIOUS_VERSION).setId(UUID.randomUUID().toString()).build());
                links.add(Models.Link.newBuilder().setType(Models.Link.LinkType.CAUSE).setId(UUID.randomUUID().toString()).build());
                LOG.debug(cmgParser.getClass().toString());
                sourceChangeCreatedEventFactory.parseFromGit(Paths.get(ns.getString("repo")).toAbsolutePath().normalize().toString(),
                    ns.getString("commit"),
                    ns.getString("branch"),
                    cmgParser
                );
                EiffelSourceChangeCreatedEvent.Builder event = (EiffelSourceChangeCreatedEventOuterClass.EiffelSourceChangeCreatedEvent.Builder) sourceChangeCreatedEventFactory.create();
                event.addAllLinks(links);
                return event.build();
            } else {
                throw new IllegalArgumentException(String.format("Illegal issue tracker chosen:%s", ns.get("tracer")));
            }
        } else {
            throw new IllegalArgumentException("Illegal factory chosen");
        }
    }

    private void extractLinks(Namespace ns, BaseFactory compositionDefinedEventFactory) {
        if(ns.getList("links") != null) {
            ns.getList("links").stream().forEach((link) -> {
                String linkString = (String)link;
                String type = linkString.split(":")[0];
                String uuid = linkString.split(":")[1];
                Link l = Models.Link.newBuilder().setType(Models.Link.LinkType.valueOf(type.toUpperCase())).setId(uuid).build();
                compositionDefinedEventFactory.addLink(l);
            });
        }
    }

}
