package fake;

import fake.model.Profile;

import java.util.List;

/**
 * Created by lior on 15/02/2017.
 */
public interface SocialAPI {
    List<Profile> list();

    Profile get(String id);

    Profile create(Profile profile);

    void delete(String id);

    void deleteAll();

    Profile merge(String id, Profile profile);
}
