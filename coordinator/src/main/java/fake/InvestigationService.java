package fake;

import fake.model.Investigation;
import org.jooby.Err;
import org.jooby.Status;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InvestigationService implements InvestigationAPI {

  private Map<String, Investigation> investigations = new ConcurrentHashMap<>();

  public InvestigationService() {
    System.out.println("Initializing InvestigationService[investigations]");
  }

  @Override
  public List<Investigation> list() {
    return new ArrayList<>(investigations.values());
  }

  @Override
  public Investigation get(final String id) {
    Investigation investigation = investigations.get(id);
    if (investigation == null) {
      throw new Err(Status.NOT_FOUND);
    }
    return investigation;
  }

  @Override
  public Investigation create(final Investigation investigation) {
    investigations.put(investigation.getId(), investigation);
    return investigation;
  }

  @Override
  public void delete(final String id) {
    investigations.remove(id);
  }

  @Override
  public void deleteAll() {
    investigations.clear();
  }


}
