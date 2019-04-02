package fake.model;

public class Investigation {
    private InvestigationState state = InvestigationState.INITIALIZE;
    private String name;
    private String description;
    private Target target;

    public InvestigationState getState() {
        return state;
    }

    public void setState(InvestigationState state) {
        this.state = state;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
