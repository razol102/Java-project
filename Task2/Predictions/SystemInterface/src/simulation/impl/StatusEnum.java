package simulation.impl;

public enum StatusEnum {
    IN_QUEUE("In queue"),
    RUNNING("Running"),
    PAUSED("Paused"),
    FINISHED("Finished");

    private String statusString;

    StatusEnum(String s) {
        this.statusString = s;
    }

    public String getStatusString() {
        return statusString;
    }
}
