package fake;

import fake.model.Profile;

import java.util.List;

/**
 * Created by lior on 15/02/2017.
 */
public interface SocialAPI {
    List<Profile> list();

    Profile get(int id);

    Profile create(Profile profile);

    void delete(int id);

    void deleteAll();

    Profile merge(int id, Profile profile);
}
