package se.lnu.thesis.event;

import com.google.common.collect.ImmutableList;
import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 19.08.12
 * Time: 11:55
 * <p/>
 * Program startup parameters list
 */
public class StartupParameters extends ApplicationEvent {

    private ImmutableList<String> args;

    public StartupParameters(Object o, String[] args) {
        super(o);

        this.args = new ImmutableList.Builder<String>().add(args).build();
    }

    public ImmutableList<String> getArgs() {
        return args;
    }
}
