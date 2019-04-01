package fake;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import fake.SocialAPI;
import fake.model.Profile;
import org.jooby.Err;
import org.jooby.Status;

@Singleton
public class InvestigationService implements SocialAPI {

  private Map<Integer, Profile> profiles = new ConcurrentHashMap<>();

  public InvestigationService() {
    System.out.println("Initializing InvestigationService[profiles]");
  }

  @Override
  public List<Profile> list() {
    return profiles.values().stream()
        .sorted(Profile.COMPARATOR)
        .collect(Collectors.toList());
  }

  @Override
  public Profile get(final int id) {
    Profile profile = profiles.get(id);
    if (profile == null) {
      throw new Err(Status.NOT_FOUND);
    }
    return profile;
  }

  @Override
  public Profile create(final Profile profile) {
    profile.setId(profiles.size() + 1);
    profiles.put(profile.getId(), profile);
    return profile;
  }

  @Override
  public void delete(final int id) {
    profiles.remove(id);
  }

  @Override
  public void deleteAll() {
    profiles.clear();
  }

  @Override
  public Profile merge(final int id, final Profile profile) {
    Profile existing = get(id);
    Optional.ofNullable(profile.getCompleted()).ifPresent(existing::setCompleted);
    Optional.ofNullable(profile.getOrder()).ifPresent(existing::setOrder);
    Optional.ofNullable(profile.getTitle()).ifPresent(existing::setTitle);
    return existing;
  }

}
