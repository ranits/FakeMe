package fake.model.commands;

import fake.model.Target;

public class ScrapeCommand {
    public static final String SCRAPE_BASE_URL = "http://localhost:8000/scrape/";

    private Target target;

    /**************************************************************************************************************/
    /** scrape API: */
    /**     POST: [/graph/:graph_name/load_graph] **/
    /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
    /**     GET: [/graph/<graph_name>/sample_details]  **/
    /**************************************************************************************************************/
    public ScrapeCommand(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}
