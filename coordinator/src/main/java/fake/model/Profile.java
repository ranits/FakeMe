package fake.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class Profile {
  private ProfileState state = ProfileState.INITIALIZE;

  private Integer id;

  private String title;

  private String imageUrl;

  private int age;

  private boolean gender;

  private boolean avatar;

  private Boolean completed;

  private Integer order;

  public ProfileState getState() {
    return state;
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

  public void setId(Integer id) {
    this.id = id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isGender() {
    return gender;
  }

  public void setGender(boolean gender) {
    this.gender = gender;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
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
  public boolean equals(final Object obj) {
    if (obj instanceof Profile) {
      return Objects.equals(id, ((Profile) obj).id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Optional.ofNullable(id).orElse(1);
  }

}
