package com.oracle.jp.shinyay.util.json.deployment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

public class Deployment {
    private String memory;
    private String instances;
    private String notes;
    private HashMap<String, String> environment;
    private Service[] services;

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getInstances() {
        return instances;
    }

    public void setInstances(String instances) {
        this.instances = instances;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public HashMap<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(HashMap<String, String> environment) {
        this.environment = environment;
    }

    public Service[] getServices() {
        return services;
    }

    public void setServices(Service[] services) {
        this.services = services;
    }
}
