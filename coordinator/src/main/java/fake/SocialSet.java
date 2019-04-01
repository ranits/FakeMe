package fake;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fake.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lior on 15/02/2017.
 */
@Singleton
public class SocialSet implements SocialAPI {
    private List<SocialAPI> stores;

    @Inject
    public SocialSet(Set<SocialAPI> stores) {
        this.stores = new ArrayList<>(stores);
    }

    @Override
    public List<Profile> list() {
        return stores.get(0).list();
    }

    @Override
    public Profile get(int id) {
        return stores.get(0).get(id);
    }

    @Override
    public Profile create(Profile profile) {
        return stores.get(0).create(profile);
    }

    @Override
    public void delete(int id) {
        stores.get(0).delete(id);

    }

    @Override
    public void deleteAll() {
        stores.get(0).deleteAll();

    }

    @Override
    public Profile merge(int id, Profile profile) {
        return stores.get(0).merge(id, profile);
    }
}
