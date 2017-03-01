package com.oracle.jp.shinyay.util.json.manifest;

public class Manifest {
    private Runtime runtime;
    private String command;
    private String startupTime;
    private String shutdownTime;
    private Release release;
    private String notes;
    private String mode;
    private String isClustered;

    public Runtime getRuntime() {
        return runtime;
    }

    public void setRuntime(Runtime runtime) {
        this.runtime = runtime;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(String startupTime) {
        this.startupTime = startupTime;
    }

    public String getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(String shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIsClustered() {
        return isClustered;
    }

    public void setIsClustered(String isClustered) {
        this.isClustered = isClustered;
    }
}
