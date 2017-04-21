package sample.multiversion;

import sample.multiversion.deps.CoreDependency;

public class ImportantCore implements Core {

    private Utility utility;
    private CoreDependency coreDependency;

    public ImportantCore() {
        utility = new Utility();
        coreDependency = new CoreDependency();
    }

    public String getVersion() {
        return utility.getVersion();
    }

    public String getDependencyVersion() {
        return coreDependency.getVersion();
    }
}