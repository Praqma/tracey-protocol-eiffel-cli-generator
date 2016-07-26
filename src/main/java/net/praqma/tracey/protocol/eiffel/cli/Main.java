package net.praqma.tracey.protocol.eiffel.cli;

import com.google.protobuf.GeneratedMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import com.google.protobuf.util.JsonFormat;
import java.util.regex.Pattern;
import net.sourceforge.argparse4j.inf.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    // Pattern to match syntax for link adding
    private static final Pattern LINKS = Pattern.compile("(CAUSE|PREVIOUS_VERSION):([0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12})",
            Pattern.CASE_INSENSITIVE);


    public static void main (String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        Namespace ns = eap.parseArgs(args);

        Logger rootLog = Logger.getLogger("");
        if (ns.getBoolean("debug") == true) {
            rootLog.setLevel( Level.DEBUG );
        } else {
            rootLog.setLevel( Level.WARN );
        }

        GeneratedMessage event = null;
        try {
            event = eap.creteEvent(args);
        } catch (Exception ex) {
            LOG.warn("Unable to create event", ex);
            System.exit(1);
        }

        if (ns.getString("file") != null) {
            File f = new File(ns.getString("file"));

            if(f.exists() && f.delete()) {
                LOG.warn("Couldn't remove " + f.toString() + ". Well, we will overwrite anyway if we can");
            }

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ns.getString("file")), "utf-8"))) {
                writer.write(JsonFormat.printer().print(event));
            }
        } else {
            LOG.warn(JsonFormat.printer().print(event));
        }
    }
}
