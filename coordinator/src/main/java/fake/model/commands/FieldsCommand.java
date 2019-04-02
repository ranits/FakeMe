package fake.model.commands;

public class FieldsCommand {
    private String profile;
    private final String age;
    private final String gender;
    private final String race;

    public FieldsCommand(String profileId, String age, String gender, String race) {
        this.profile = profileId;
        this.age = age;
        this.gender = gender;
        this.race = race;
    }


    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
