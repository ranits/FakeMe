package fake.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
  private ProfileState state = ProfileState.INITIALIZE;

  private String id;

  private Properties properties;

  private String imageUrl;

  private Boolean completed;

  private Boolean avatar;

  private Integer order;

  public ProfileState getState() {
    return state;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public void setState(ProfileState state) {
    this.state = state;
  }

  public static Comparator<Profile> COMPARATOR = Comparator.comparing(Profile::getOrder);

  public boolean isAvatar() {
    return avatar;
  }

  public void setAvatar(boolean avatar) {
    this.avatar = avatar;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Boolean getCompleted() {
    return Optional.ofNullable(completed).orElse(Boolean.FALSE);
  }

  public void setCompleted(final Boolean completed) {
    this.completed = completed;
  }

  public Integer getOrder() {
    return Optional.ofNullable(order).orElse(0);
  }

  public void setOrder(final Integer order) {
    this.order = order;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Profile profile = (Profile) o;
    return state == profile.state &&
            Objects.equals(id, profile.id) &&
            Objects.equals(properties, profile.properties) &&
            Objects.equals(imageUrl, profile.imageUrl) &&
            Objects.equals(completed, profile.completed) &&
            Objects.equals(avatar, profile.avatar) &&
            Objects.equals(order, profile.order);
  }

  @Override
  public int hashCode() {

    return Objects.hash(state, id, properties, imageUrl, completed, avatar, order);
  }
}
