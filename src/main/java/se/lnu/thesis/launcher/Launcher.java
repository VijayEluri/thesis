package se.lnu.thesis.launcher;

import com.beust.jcommander.JCommander;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import se.lnu.thesis.event.ClusterGraphLoaded;
import se.lnu.thesis.event.GOGraphLoaded;
import se.lnu.thesis.event.StartupParameters;
import se.lnu.thesis.io.IOFacade;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 19.08.12
 * Time: 11:26
 * <p/>
 * Launcher parameters parser
 */
@Service
public class Launcher implements ApplicationListener {

    public static final Logger LOGGER = Logger.getLogger(Launcher.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private IOFacade ioFacade;

    protected JCommander commander;
    protected LoadCommand loadCommand;

    @PostConstruct
    public void init() {
        loadCommand = new LoadCommand();

        commander = new JCommander();
        commander.addCommand(LoadCommand.NAME, loadCommand);
    }

    public void parse(List<String> args) {

        try {
            commander.parse(args.toArray(new String[args.size()]));
        } catch (Exception e) {
            LOGGER.error("Error parsing startup parameters", e);
        }

        if (commander.getParsedCommand() == LoadCommand.NAME) {
            LOGGER.info(loadCommand.toString());

            if (loadCommand.getType().equals("gml")) {
                publisher.publishEvent(new GOGraphLoaded(this, ioFacade.loadMyGraphFromGml(loadCommand.getGo())));
                publisher.publishEvent(new ClusterGraphLoaded(this, ioFacade.loadMyGraphFromGml(loadCommand.getCluster())));
            }


        }

    }


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof StartupParameters) {
            parse(StartupParameters.class.cast(event).getArgs());
        }
    }
}
