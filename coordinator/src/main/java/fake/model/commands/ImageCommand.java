package fake.model.commands;

import fake.model.Profile;

public class ImageCommand {
    public static final String IMAGE_BASE_URL = "http://localhost:8020/image/";

    private String investigationId;
    private Profile profile;

    /**************************************************************************************************************/
    /** Image API: */
    /**     POST: [/graph/:graph_name/load_graph] **/
    /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
    /**     GET: [/graph/<graph_name>/sample_details]  **/
    /**************************************************************************************************************/
    public ImageCommand(String investigationId, Profile profile) {
        this.investigationId = investigationId;
        this.profile = profile;
    }

    public String getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(String investigationId) {
        this.investigationId = investigationId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
