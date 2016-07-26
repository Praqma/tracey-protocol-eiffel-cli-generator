/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.tracey.protocol.eiffel;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.util.JsonFormat;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Mads
 */
public class ParseEiffelCompositionDefinedEventTest {

    @Test
    public void testCompositonEventCreateCommand() throws Exception {
       String[] args = new String[] {"EiffelCompositionDefinedEvent", "-l", "PREVIOUS_VERSION:8a718a03-f473-4e61-9bae-e986885fee18", "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18"};
       EiffelArgumentParser eap = new EiffelArgumentParser();
       eap.registerAllParsers();
       Namespace ns = eap.parseArgs(args);
       assertNotNull(ns.getList("links"));
       GeneratedMessage msg = eap.creteEvent(args);
       System.out.println(JsonFormat.printer().print(msg));
    }

}
