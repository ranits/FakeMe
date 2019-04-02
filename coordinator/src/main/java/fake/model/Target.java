package fake.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Target {
    private String socialNetwork;

    private String scrapeResultUrl;

    private List<String> targets;

    private boolean group;

    public boolean isGroup() {
        return group;
    }

    public String getScrapeResultUrl() {
        return scrapeResultUrl;
    }

    public void setScrapeResultUrl(String scrapeResultUrl) {
        this.scrapeResultUrl = scrapeResultUrl;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }
}

