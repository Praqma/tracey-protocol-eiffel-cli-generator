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
import net.sourceforge.argparse4j.inf.*;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main (String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // To avoid log4j printouts when the program starts
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.WARN);

        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        Namespace ns = null;
        try {
            ns = eap.parseArgs(args);
        } catch (HelpScreenException hse) {
            // Special case with the help.
            return;
        } catch (ArgumentParserException ex) {
            LOG.warn("Failed to parse arguments", ex);
            System.exit(3);
        }

        Logger rootLog = Logger.getLogger("");
        if (ns != null && ns.getBoolean("debug") == true) {
            rootLog.setLevel( Level.DEBUG );
        } else {
            rootLog.setLevel( Level.WARN );
        }

        GeneratedMessage event = null;
        try {
            event = eap.createEvent(args);
        } catch (Exception ex) {
            LOG.warn("Unable to create event", ex);
            ex.printStackTrace(System.out);
            System.exit(1);
        }

        if (ns != null && ns.getString("file") != null) {
            File f = new File(ns.getString("file"));

            if(f.exists() && f.delete()) {
                LOG.warn("Removed " + f.toString() + ". Well, we will overwrite anyway if we can");
            }

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ns.getString("file")), "utf-8"))) {
                writer.write(JsonFormat.printer().print(event));
            }
        } else {
            LOG.warn(JsonFormat.printer().print(event));
        }
    }
}
