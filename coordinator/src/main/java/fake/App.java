package fake;

import fake.model.Profile;
import org.jooby.Jooby;
import org.jooby.RequestLogger;
import org.jooby.Results;
import org.jooby.Status;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;
import org.jooby.scanner.Scanner;

import java.util.List;
import java.util.function.Consumer;

/**
 * Source code for http://www.todobackend.com.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class App extends Jooby {

    {
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

        /** Compute absolute Profile URL: */
        after("/profile/**", (req, rsp, result) -> {
            Consumer<Profile> computeUrl = (profile) -> profile
                    .setImageUrl("http://" + req.header("host").value("") + "/profile/" + profile.getId());
            Object value = result.get();
            if (value instanceof Profile) {
                computeUrl.accept((Profile) value);
            } else if (value instanceof List) {
                ((List) value).forEach(computeUrl);
            }
            return result;
        });

        /** Profile API: */
        get("/profiles",(request) -> {
                    SocialAPI store = getStore();
                    return store.list();
                });
                /** Get todo by ID. */
        get("/profiles/:id", req -> {
                    SocialAPI store = getStore();
                    return store.get(req.param("id").intValue());
                });
                /** Create a new todo. */
        post("/profiles",req -> {
                    SocialAPI store = getStore();
                    return Results.with(store.create(req.body(Profile.class)), Status.CREATED);
                });
                /** Delete todo by ID. */
        delete("/:id", req -> {
                    SocialAPI store = getStore();
                    store.delete(req.param("id").intValue());
                    return Results.noContent();
                });
                /** Delete all todos. */
         delete(() -> {
                    SocialAPI store = getStore();
                    store.deleteAll();
                    return Results.noContent();
                });
                /** Update an existing todo. */
         patch("/:id", req -> {
                    SocialAPI store = getStore();
                    return store.merge(req.param("id").intValue(), req.body(Profile.class));
                });
        use(new ApiTool()
                .swagger("/swagger")
        );
        use("/swagger",
                req -> Results.redirect("/public/assets/swagger/swagger.json"));
        //swagger support
        assets("public/assets/**");

    }

    private SocialAPI getStore() {
        return require(SocialAPI.class);
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
