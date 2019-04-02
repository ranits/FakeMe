package fake;

import com.fasterxml.jackson.core.type.TypeReference;
import fake.model.Investigation;
import fake.model.InvestigationState;
import fake.model.Profile;
import fake.model.Target;
import fake.model.commands.GraphCommand;
import fake.model.commands.ImageCommand;
import fake.model.commands.ScrapeCommand;
import org.jooby.Jooby;
import org.jooby.RequestLogger;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.apitool.ApiTool;
import org.jooby.handlers.CorsHandler;
import org.jooby.json.Jackson;
import org.jooby.scanner.Scanner;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static fake.Utils.call;
import static fake.Utils.unwrap;
import static fake.model.commands.GraphCommand.GRAPH_BASE_URL;
import static fake.model.commands.ImageCommand.IMAGE_BASE_URL;
import static fake.model.commands.ScrapeCommand.SCRAPE_BASE_URL;

/**
 * Source code for http://www.todobackend.com.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class App extends Jooby {
    {
        use("*", new CorsHandler());
        use(new Scanner());
        use("*", new RequestLogger().extended());
        use(new fake.Module());
        /** JSON: */
        use(new Jackson());

        /** CORS: */
        use("*", (req, rsp) -> {
            rsp.header("Access-Control-Allow-Origin", "*");
            rsp.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH");
            rsp.header("Access-Control-Max-Age", "3600");
            rsp.header("Access-Control-Allow-Headers", "x-requested-with", "origin", "content-type",
                    "accept");
            if (req.method().equalsIgnoreCase("options")) {
                rsp.end();
            }
        });

        /** WebSocket state broadcast: */
        /**************************************************************************************************************/
        try {
            Utils.ws("localhost:9998");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        /** Investigation API: */
        /**************************************************************************************************************/
        get("/investigations", (request) -> {
            InvestigationAPI store = getInvestigation();
            return store.list();
        });

        post("/investigation", req -> {
            return Results.with(getInvestigation().create(req.body(Investigation.class)), Status.CREATED);
        });

        /** Get todo by ID. */
        get("/investigation/:id", req -> {
            return getInvestigation().get(req.param("id").value());
        });

        post("/investigation/:id/target", (req) -> {
            Investigation investigation = getInvestigation().get(req.param("id").value());
            Target target = req.body(Target.class);
            investigation.setTarget(target);
            return target;
        });


        get("/investigation/:id/state/:state", (req) -> {
            Investigation investigation = getInvestigation().get(req.param("id").value());
            InvestigationState state = InvestigationState.valueOf(req.param("state").value().toUpperCase());
            investigation.setState(state);
            return state;
        });

        /**
         * Life Cycle commands
         * ************************************************************************************************************/

        get("/investigation/:id/scrape", (req) -> {
            Investigation investigation = getInvestigation().get(req.param("id").value());
            Target target = investigation.getTarget();
            ScrapeCommand command = new ScrapeCommand(target);
            Utils.CallResponse<Object> response = call(SCRAPE_BASE_URL, command);
            if (response.code == 200) {
                investigation.setState(InvestigationState.SCRAPING);
            } else {
                return response;
            }
            return target;
        });

        /**************************************************************************************************************/
        /** Graph API: */
        /**     POST: [/graph/:graph_name/load_graph] **/
        /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
        /**     GET: [/graph/<graph_name>/sample_details]  **/
        /**************************************************************************************************************/
        post("/investigation/:id/graph/:algo", (req) -> {
            String id = req.param("id").value();
            String algo = req.param("algo").value();

            Investigation investigation = getInvestigation().get(id);
            Target target = investigation.getTarget();
            investigation.setState(InvestigationState.GRAPH_PROCESSING);

            GraphCommand command = new GraphCommand(investigation.getName(), target.getScrapeResultUrl(), algo);
            Utils.CallResponse<Object> response = call(GRAPH_BASE_URL, command);

            if (response.code == 200) {
                List<Map<String, String >> profiles = unwrap(response.body.toString(), new TypeReference<List<Map<String, String>>>() {});
                Profile profile = createProfile(profiles);
                return profile;
            } else {
                investigation.setState(InvestigationState.ERROR);
                return investigation;
            }
        });


        /**************************************************************************************************************/
        /** Image API: */
        /**************************************************************************************************************/
        post("/investigation/:id/profile/:pid/image", (req) -> {
            String id = req.param("id").value();
            String pid = req.param("pid").value();

            Investigation investigation = getInvestigation().get(id);
            Profile profile = getProfiles().get(pid);

            ImageCommand command = new ImageCommand(investigation.getName(),profile);
            Utils.CallResponse<Object> response = call(IMAGE_BASE_URL, command);

            if (response.code == 200) {
                investigation.setState(InvestigationState.SCRAPING);
                return investigation;
            } else {
                return response;
            }
        });


        /**************************************************************************************************************/
        /** Profile API: */
        /**************************************************************************************************************/
        get("/profiles", (request) -> {
            SocialAPI store = getProfiles();
            return store.list();
        });
        /** Get todo by ID. */
        get("/profiles/:id", req -> {
            SocialAPI store = getProfiles();
            return store.get(req.param("id").value());
        });
        /** Create a new todo. */
        post("/profiles", req -> {
            SocialAPI store = getProfiles();
            return Results.with(store.create(req.body(Profile.class)), Status.CREATED);
        });
        /** Delete todo by ID. */
        delete("/:id", req -> {
            SocialAPI store = getProfiles();
            store.delete(req.param("id").value());
            return Results.noContent();
        });
        /** Delete all todos. */
        delete(() -> {
            SocialAPI store = getProfiles();
            store.deleteAll();
            return Results.noContent();
        });
        /** Update an existing todo. */
        patch("/:id", req -> {
            SocialAPI store = getProfiles();
            return store.merge(req.param("id").value(), req.body(Profile.class));
        });

        /**************************************************************************************************************/
        use(new ApiTool()
                .swagger("/swagger")
        );
        use("/swagger",
                req -> Results.redirect("/public/assets/swagger/swagger.json"));
        //swagger support
        assets("public/assets/**");

    }

    private Profile createProfile(List<Map<String,String>> profiles) {
        Profile profile = new Profile();
        Properties properties = new Properties();
        properties.putAll(profiles.get(0));
        profile.setProperties(properties);
        return getProfiles().create(profile);
    }

    private SocialAPI getProfiles() {
        return require(SocialAPI.class);
    }

    private InvestigationAPI getInvestigation() {
        return require(InvestigationAPI.class);
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
