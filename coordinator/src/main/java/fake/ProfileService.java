package fake;

import fake.model.Profile;
import org.jooby.Err;
import org.jooby.Status;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class ProfileService implements SocialAPI {

  private Map<String, Profile> profiles = new ConcurrentHashMap<>();

  public ProfileService() {
    System.out.println("Initializing InvestigationService[profiles]");
  }

  @Override
  public List<Profile> list() {
    return profiles.values().stream()
        .sorted(Profile.COMPARATOR)
        .collect(Collectors.toList());
  }

  @Override
  public Profile get(final String id) {
    Profile profile = profiles.get(id);
    if (profile == null) {
      throw new Err(Status.NOT_FOUND);
    }
    return profile;
  }

  @Override
  public Profile create(final Profile profile) {
    profile.setId(Integer.toString(profiles.size() + 1));
    profiles.put(profile.getId(), profile);
    return profile;
  }

  @Override
  public void delete(final String id) {
    profiles.remove(id);
  }

  @Override
  public void deleteAll() {
    profiles.clear();
  }

  @Override
  public Profile merge(final String id, final Profile profile) {
    Profile existing = get(id);
    Optional.ofNullable(profile.getCompleted()).ifPresent(existing::setCompleted);
    Optional.ofNullable(profile.getOrder()).ifPresent(existing::setOrder);
    return existing;
  }

}
