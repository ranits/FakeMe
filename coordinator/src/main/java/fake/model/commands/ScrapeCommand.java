package fake.model.commands;

import fake.model.Target;

public class ScrapeCommand {
    private Target target;

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
