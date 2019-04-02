package fake;

import fake.model.Investigation;

import java.util.List;

/**
 * Created by lior on 15/02/2017.
 */
public interface InvestigationAPI {
    List<Investigation> list();

    Investigation get(String id);

    Investigation create(Investigation profile);

    void delete(String id);

    void deleteAll();

}
