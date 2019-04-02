package fake.model.commands;

public class GraphCommand {
    public static final String GRAPH_BASE_URL = "http://localhost:8010/graph/";

    private String algo;
    private String investigationName;
    private String urlLocation;

    /**************************************************************************************************************/
    /** Graph API: */
    /**     POST: [/graph/:graph_name/load_graph] **/
    /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
    /**     GET: [/graph/<graph_name>/sample_details]  **/
    /**************************************************************************************************************/
    public GraphCommand(String investigationName,String urlLocation, String algo) {
        this.investigationName = investigationName;
        this.urlLocation = urlLocation;
        this.algo = algo;
    }

    public String getInvestigationName() {
        return investigationName;
    }

    public void setInvestigationName(String investigationName) {
        this.investigationName = investigationName;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public String getUrlLocation() {
        return urlLocation;
    }

    public void setUrlLocation(String urlLocation) {
        this.urlLocation = urlLocation;
    }
}

