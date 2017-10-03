package net.praqma.tracey.protocol.eiffel.cli;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.praqma.tracey.protocol.eiffel.models.Models;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.*;

/**
 * Created by mads on 10/3/17.
 */
public class EiffelSourceChangeSubmittedEventParser {
    public EiffelSourceChangeSubmittedEventParser(Subparser parser) {
        parser.addArgument("-j", "--json").dest("json").help("Pass a target json file path this in to add the SCC meta id as link CHANGE");
        parser.addArgument("-l", "--links").dest("links").nargs("*");
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
    }


    public static Models.Link changeLinkFromJson(String sourceFile) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFile);
        try(JsonReader reader = new JsonReader(new InputStreamReader(fis, "utf-8"))) {
            JsonElement ele = new JsonParser().parse(reader);
            return Models.Link.newBuilder().setId(ele.getAsJsonObject().getAsJsonObject("meta").get("id").getAsString()).setType(Models.Link.LinkType.CHANGE).build();
        }
    }
}
