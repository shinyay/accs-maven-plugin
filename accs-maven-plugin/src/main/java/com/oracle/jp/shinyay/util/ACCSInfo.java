package com.oracle.jp.shinyay.util;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.util.HashMap;
import java.util.Properties;

public class ACCSInfo {

    private final String region;
    private final String identityDomain;
    private final String username;
    private final String password;

    private final String appName;
    private final String runtime;
    private final String subscription;
    private final String archiveURL;

    private final String metaJsonPath;
    private final String outputPath;
    private final String archiveName;

    private final String majorVersion;
    private final String command;

    private final String startupTime;
    private final String shutdownTime;
    private final String build;
    private final String commit;
    private final String version;
    private final String notes;
    private final String mode;
    private final String isClustered;

    private final String memory;
    private final String instances;
    private final String deployment_notes;
    private final HashMap<String, String> environment;
    private final String[] identifiers;
    private final String[] types;
    private final String[] names;
    private final String[] usernames;
    private final String[] passwords;

    private final Log log;

    public static class ACCSInfoBuilder {
        private final String region;
        private final String identityDomain;
        private final String username;
        private final String password;
        private final String runtime;

        private String appName;
        private String subscription;
        private String archiveURL;

        private String metaJsonPath;
        private String outputPath;
        private String archiveName;

        private final String majorVersion;
        private final String command;

        private String startupTime;
        private String shutdownTime;
        private String build;
        private String commit;
        private String version;
        private String notes;
        private String mode;
        private String isClustered;

        private String memory;
        private String instances;
        private String deployment_notes;
        private HashMap<String, String> environment;
        private String[] identifiers;
        private String[] types;
        private String[] names;
        private String[] usernames;
        private String[] passwords;

        private Log log;

        public ACCSInfoBuilder(String region, String identityDomain, String username, String password, String runtime, String majorVersion, String command) {
            this.region = region;
            this.identityDomain = identityDomain;
            this.username = username;
            this.password = password;
            this.runtime = runtime;
            this.majorVersion = majorVersion;
            this.command = command;
            this.log = new SystemStreamLog();
        }

        public ACCSInfoBuilder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public ACCSInfoBuilder subscription(String subscription) {
            this.subscription = subscription;
            return this;
        }

        public ACCSInfoBuilder archiveURL(String archiveURL) {
            this.archiveURL = archiveURL;
            return this;
        }

        public ACCSInfoBuilder startupTime(String startupTime) {
            this.startupTime = startupTime;
            return this;
        }

        public ACCSInfoBuilder shutdownTime(String shutdownTime) {
            this.shutdownTime = shutdownTime;
            return this;
        }

        public ACCSInfoBuilder build(String build) {
            this.build = build;
            return this;
        }

        public ACCSInfoBuilder commit(String commit) {
            this.commit = commit;
            return this;
        }

        public ACCSInfoBuilder version(String version) {
            this.version = version;
            return this;
        }

        public ACCSInfoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public ACCSInfoBuilder mode(String mode) {
            this.mode = mode;
            return this;
        }

        public ACCSInfoBuilder isClustered(String isClustered) {
            this.isClustered = isClustered;
            return this;
        }

        public ACCSInfoBuilder memory(String memory) {
            this.memory = memory;
            return this;
        }

        public ACCSInfoBuilder instances(String instances) {
            this.instances = instances;
            return this;
        }

        public ACCSInfoBuilder deployment_notes(String deployment_notes) {
            this.deployment_notes = deployment_notes;
            return this;
        }

        public ACCSInfoBuilder environment(HashMap<String, String> environment) {
            this.environment = environment;
            return this;
        }

        public ACCSInfoBuilder identifiers(String[] identifiers) {
            this.identifiers = identifiers;
            return this;
        }

        public ACCSInfoBuilder types(String[] types) {
            this.types = types;
            return this;
        }

        public ACCSInfoBuilder names(String[] names) {
            this.names = names;
            return this;
        }

        public ACCSInfoBuilder usernames(String[] usernames) {
            this.usernames = usernames;
            return this;
        }

        public ACCSInfoBuilder passwords(String[] passwords) {
            this.passwords = passwords;
            return this;
        }

        public ACCSInfoBuilder metaJsonPath(String metaJsonPath) {
            this.metaJsonPath = metaJsonPath;
            return this;
        }

        public ACCSInfoBuilder outputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public ACCSInfoBuilder archiveName(String archiveName) {
            this.archiveName = archiveName;
            return this;
        }

        public ACCSInfoBuilder log(Log log) {
            this.log = log;
            return this;
        }

        public ACCSInfo build() {
            return new ACCSInfo(this);
        }
    }

    private ACCSInfo(ACCSInfoBuilder builder) {
        this.region = builder.region;
        this.identityDomain = builder.identityDomain;
        this.username = builder.username;
        this.password = builder.password;
        this.appName = builder.appName;
        this.runtime = builder.runtime;
        this.subscription = builder.subscription;
        this.archiveURL = builder.archiveURL;
        this.majorVersion = builder.majorVersion;
        this.command = builder.command;
        this.startupTime = builder.startupTime;
        this.shutdownTime = builder.shutdownTime;
        this.build = builder.build;
        this.commit = builder.commit;
        this.version = builder.version;
        this.notes = builder.notes;
        this.mode = builder.mode;
        this.isClustered = builder.isClustered;
        this.memory = builder.memory;
        this.instances = builder.instances;
        this.deployment_notes = builder.deployment_notes;
        this.environment = builder.environment;
        this.identifiers = builder.identifiers;
        this.types = builder.types;
        this.names = builder.names;
        this.usernames = builder.usernames;
        this.passwords = builder.passwords;
        this.metaJsonPath = builder.metaJsonPath;
        this.outputPath = builder.outputPath;
        this.archiveName = builder.archiveName;
        this.log = builder.log;
    }

    public String getRegion() {
        return region;
    }

    public String getIdentityDomain() {
        return identityDomain;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAppName() {
        return appName;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getSubscription() {
        return subscription;
    }

    public String getArchiveURL() {
        return archiveURL;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public String getCommand() {
        return command;
    }

    public String getStartupTime() {
        return startupTime;
    }

    public String getShutdownTime() {
        return shutdownTime;
    }

    public String getBuild() {
        return build;
    }

    public String getCommit() {
        return commit;
    }

    public String getVersion() {
        return version;
    }

    public String getNotes() {
        return notes;
    }

    public String getMode() {
        return mode;
    }

    public String getIsClustered() {
        return isClustered;
    }

    public String getMemory() {
        return memory;
    }

    public String getInstances() {
        return instances;
    }

    public String getDeployment_notes() {
        return deployment_notes;
    }

    public HashMap<String, String> getEnvironment() {
        return environment;
    }

    public String[] getIdentifiers() {
        return identifiers;
    }

    public String[] getTypes() {
        return types;
    }

    public String[] getNames() {
        return names;
    }

    public String[] getUsernames() {
        return usernames;
    }

    public String[] getPasswords() {
        return passwords;
    }

    public String getMetaJsonPath() {
        return metaJsonPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public Log getLog() {
        return log;
    }

}
