package fake;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import org.jooby.Env;
import org.jooby.Jooby;

public class Module implements Jooby.Module {

    @Override
    public void configure(Env env, Config conf, Binder binder) {
        binder.bind(SocialAPI.class).to(InvestigationService.class);
    }
}
