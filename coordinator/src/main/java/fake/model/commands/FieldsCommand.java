package fake.model.commands;

public class FieldsCommand {
    public static final String NLP_BASE_URL = "http://localhost:8030/fields/";

    private String profile;
    private final String age;
    private final String gender;
    private final String race;

    /**************************************************************************************************************/
    /** NLP API: */
    /**     POST: [/graph/:graph_name/load_graph] **/
    /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
    /**     GET: [/graph/<graph_name>/sample_details]  **/
    /**************************************************************************************************************/
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
