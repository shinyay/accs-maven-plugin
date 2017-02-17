package com.oracle.jp.shinyay;

import com.oracle.jp.shinyay.util.ACCSFunction;
import com.oracle.jp.shinyay.util.ACCSInfo;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Mojo(name = "create-manifest", threadSafe = true, defaultPhase = LifecyclePhase.INSTALL)
public class ACCSCreateManifestMojo extends AbstractMojo {

    @Parameter(required = true)
    private String region;
    @Parameter(required = true)
    private String identitydomain;
    @Parameter(required = true)
    private String user;
    @Parameter(required = true)
    private String password;
    @Parameter(required = true)
    private String storage;

    @Parameter(defaultValue = "${project.artifactId}-${project.version}")
    private String appName;
    @Parameter(required = true)
    private String runtime;
    @Parameter(defaultValue = "Monthly")
    private String subscription;
    @Parameter(defaultValue = "_apaas/${project.artifactId}-${project.version}.zip")
    private String archiveURL;

    @Parameter(defaultValue = "${project.build.directory}")
    private String outputPath;
    @Parameter(defaultValue = "${basedir}")
    private String metaJsonPath;
    @Parameter(defaultValue = "${project.artifactId}-${project.version}", readonly = true)
    private String archiveName;

    @Parameter(required = true)
    private String majorVersion;
    @Parameter(required = true)
    private String command;
    @Parameter(defaultValue = "300")
    private String startupTime;
    @Parameter(defaultValue = "0")
    private String shutdownTime;
    @Parameter()
    private String build;
    @Parameter()
    private String commit;
    @Parameter()
    private String version;
    @Parameter()
    private String notes;
    @Parameter()
    private String mode;
    @Parameter()
    private String isClustered;

    @Parameter()
    private String memory;
    @Parameter()
    private String instances;
    @Parameter()
    private String deployment_notes;
    @Parameter()
    private HashMap<String, String> environment;
    @Parameter()
    private String[] identifiers;
    @Parameter()
    private String[] types;
    @Parameter()
    private String[] names;
    @Parameter()
    private String[] usernames;
    @Parameter()
    private String[] passwords;


    private static ACCSInfo accsInfo;

    public void execute() throws MojoExecutionException, MojoFailureException {
        initializeACCSInfo();
        createManifestJson();
    }

    private void initializeACCSInfo() {
        accsInfo = new ACCSInfo.ACCSInfoBuilder(region, identitydomain, user, password, runtime, majorVersion, command)
                .appName(Optional.ofNullable(appName).orElseGet(() -> "ACCS-SAMPLE"))
                .subscription(Optional.ofNullable(subscription).orElseGet(() -> "Monthly"))
                .archiveURL(Optional.ofNullable(archiveURL).orElseGet(() -> ""))
                .startupTime(Optional.ofNullable(startupTime).orElseGet(() -> "300"))
                .shutdownTime(Optional.ofNullable(shutdownTime).orElseGet(() -> "0"))
                .build(Optional.ofNullable(build)
                        .orElseGet(() -> DateTimeFormatter
                                .ofPattern("yymmdd.HHmmss")
                                .format(LocalDateTime.now())))
                .commit(Optional.ofNullable(commit).orElseGet(() -> getGitHash()))
                .version(Optional.ofNullable(version).orElseGet(() -> ""))
                .notes(Optional.ofNullable(notes).orElseGet(() -> ""))
                .mode(Optional.ofNullable(mode).orElseGet(() -> "rolling"))
                .isClustered(Optional.ofNullable(isClustered).orElseGet(() -> "false"))
                .memory(Optional.ofNullable(memory).orElseGet(() -> "1G"))
                .instances(Optional.ofNullable(instances).orElseGet(() -> "1"))
                .deployment_notes(Optional.ofNullable(deployment_notes).orElseGet(() -> ""))
                .environment(environment)
                .identifiers(identifiers)
                .types(types)
                .names(names)
                .usernames(usernames)
                .passwords(passwords)
                .metaJsonPath(Optional.ofNullable(metaJsonPath).orElseGet(() -> System.getProperty("user.dir")))
                .outputPath(Optional.ofNullable(outputPath).orElseGet(() -> System.getProperty("user.dir")))
                .archiveName(Optional.ofNullable(archiveName).orElseGet(() -> "ACCS-ARCHIVE"))
                .log(getLog())
                .build();
        getLog().debug("IDENTITY-DOMAIN: " + accsInfo.getIdentityDomain());
        getLog().debug("USER: " + accsInfo.getUsername());
        getLog().debug("PASSWORD: " + accsInfo.getPassword());
        getLog().debug("MAJOR-VERSION: " + accsInfo.getMajorVersion());
        getLog().debug("COMMAND: " + accsInfo.getCommand());
        getLog().debug("STARTUP-TIME: " + accsInfo.getStartupTime());
        getLog().debug("SHUTDOWN-TIME: " + accsInfo.getShutdownTime());
        getLog().debug("BUILD: " + accsInfo.getBuild());
        getLog().debug("COMMIT: " + accsInfo.getCommit());
        getLog().debug("VERSION: " + accsInfo.getVersion());
        getLog().debug("NOTES: " + accsInfo.getNotes());
        getLog().debug("MODE: " + accsInfo.getMode());
        getLog().debug("VERSION: " + accsInfo.getVersion());
        getLog().debug("IS-CLUSTER: " + accsInfo.getIsClustered());
    }

    private void createManifestJson() {
        try {
            String result = ACCSFunction.createManifestJsonForJava(accsInfo);
            getLog().info("MANIFEST-JSON: " + result);
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    private String getGitHash() {
        try {
            return new BufferedReader(
                    new InputStreamReader(
                            new ProcessBuilder("git", "log", "--pretty='%H'", "-1").start().getInputStream(), "UTF-8"))
                    .lines()
                    .findFirst().orElseGet(() -> "NOT-COMMITTED-YET");
        } catch (IOException ioe) {
            getLog().error(ioe);
            return null;
        }
    }
}
