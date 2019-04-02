package fake.model.commands;

public class GraphCommand {
    private String algo;
    private String investigationName;
    private String urlLocation;

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

