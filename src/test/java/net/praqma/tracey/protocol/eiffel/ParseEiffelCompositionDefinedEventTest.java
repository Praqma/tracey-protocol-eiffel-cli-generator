package net.praqma.tracey.protocol.eiffel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.praqma.tracey.protocol.eiffel.cli.EiffelArgumentParser;
import net.praqma.tracey.protocol.eiffel.cli.Main;
import net.praqma.tracey.protocol.eiffel.events.EiffelCompositionDefinedEventOuterClass.EiffelCompositionDefinedEvent;
import net.praqma.tracey.protocol.eiffel.models.Models.Link;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import static org.hamcrest.CoreMatchers.not;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class ParseEiffelCompositionDefinedEventTest {

    private final File testFile = new File("testMain_Compsition.json");

    @Test
    public void testCompositonEventCreateCommand() throws Exception {
        String[] args = new String[] {"EiffelCompositionDefinedEvent", "-l", "PREVIOUS_VERSION:8a718a03-f473-4e61-9bae-e986885fee18", "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();

        Link l1 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.CAUSE).build();
        Link l2 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.PREVIOUS_VERSION).build();
        Namespace ns = eap.parseArgs(args);
        assertNotNull(ns.getList("links"));
        EiffelCompositionDefinedEvent msg = (EiffelCompositionDefinedEvent)eap.createEvent(args);
        assertEquals("Composition name", msg.getData().getName());
        assertThat(msg.getLinksList(), hasItems(l1, l2));
    }

    @Test
    public void testCompositonEventCreateCommandWithIncorrectLink() throws Exception {
        String[] args = new String[] {"EiffelCompositionDefinedEvent", "-l", "NOT_THERE:8a718a03-f473-4e61-9bae-e986885fee18", "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();

        Link l1 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.CAUSE).build();
        Link l2 = Link.newBuilder().setId("8a718a03-f473-4e61-9bae-e986885fee18").setType(Link.LinkType.PREVIOUS_VERSION).build();

        Namespace ns = eap.parseArgs(args);
        assertNotNull(ns.getList("links"));
        EiffelCompositionDefinedEvent msg = (EiffelCompositionDefinedEvent)eap.createEvent(args);
        assertEquals("Composition name", msg.getData().getName());
        assertThat(msg.getLinksList(), hasItems(l1));
        assertThat(msg.getLinksList(), not(hasItems(l2)));
    }

    /**
     * Use case: We want to test the main interface of the CLI when used in our .jar,
     * that is invoking it using the main method of the main class.
     *
     *
     * @throws Exception when the test fails.
     */
    @Test
    public void testMain() throws Exception {
        String[] args = new String[] {
           "-f", testFile.getAbsolutePath(),
           "EiffelCompositionDefinedEvent",
           "-l", "PREVIOUS_VERSION:8a718a03-f473-4e61-9bae-e986885fee18", "CAUSE:8a718a03-f473-4e61-9bae-e986885fee18"};
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        Main.main(args);
        assertTrue(testFile.exists());
        String contents =  new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())),"UTF-8");
        assertTrue(contents.contains("EiffelCompositionDefinedEvent"));
    }

    @Test(expected = HelpScreenException.class)
    public void helpScreen() throws Exception {
        String[] args = new String[] { "EiffelCompositionDefinedEvent", "-h" };
        EiffelArgumentParser eap = new EiffelArgumentParser();
        eap.registerAllParsers();
        eap.parseArgs(args);
    }

    @After
    public void doCleanup() throws Exception {
        if(testFile.exists() && !testFile.delete()) {
            throw new IOException("Can't delete " + testFile.getAbsolutePath());
        }
    }

}
