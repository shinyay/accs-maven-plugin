package com.oracle.jp.shinyay.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oracle.jp.shinyay.http.GetMethod;
import com.oracle.jp.shinyay.http.PostMethod;
import com.oracle.jp.shinyay.util.json.deployment.Deployment;
import com.oracle.jp.shinyay.util.json.deployment.Service;
import com.oracle.jp.shinyay.util.json.manifest.Manifest;
import com.oracle.jp.shinyay.util.json.manifest.Release;
import com.oracle.jp.shinyay.util.json.manifest.Runtime;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ACCSFunction {
    private ACCSFunction() {
    }

    public static String createManifestJsonForJava(ACCSInfo accsInfo) throws IOException {
        Runtime runtime = new Runtime();
        runtime.setMajorVersion(accsInfo.getMajorVersion());

        Release release = new Release();
        release.setBuild(accsInfo.getBuild());
        release.setCommit(accsInfo.getCommit());
        release.setVersion(accsInfo.getVersion());

        Manifest manifest = new Manifest();
        manifest.setRuntime(runtime);
        manifest.setCommand(accsInfo.getCommand());
        manifest.setStartupTime(accsInfo.getStartupTime());
        manifest.setShutdownTime(accsInfo.getShutdownTime());
        manifest.setRelease(release);
        manifest.setNotes(accsInfo.getNotes());
        manifest.setMode(accsInfo.getMode());
        manifest.setIsClustered(accsInfo.getIsClustered());

        return JsonConverter.createJsonFromJava(manifest, accsInfo.getMetaJsonPath(), ACCSConstants.METAFILES_MANIFEST);
    }

    public static String createManifestJsonForNode(ACCSInfo accsInfo) {
        return "";
    }

    public static String createManifestJsonForPHP(ACCSInfo accsInfo) {
        return "";
    }

    public static String createDeploymentJson(ACCSInfo accsInfo) throws IOException {
        Service[] services = null;
        if (accsInfo.getIdentifiers() != null) {
            services = new Service[accsInfo.getIdentifiers().length];
            for (int idx = 0; idx < services.length; idx++) {
                services[idx] = new Service();
                services[idx].setIdentifier(accsInfo.getIdentifiers()[idx]);
                services[idx].setType(accsInfo.getTypes()[idx]);
                services[idx].setName(accsInfo.getNames()[idx]);
                services[idx].setUsername(accsInfo.getUsernames()[idx]);
                services[idx].setPassword(accsInfo.getPasswords()[idx]);
            }
        }

        Deployment deployment = new Deployment();
        deployment.setMemory(accsInfo.getMemory());
        deployment.setInstances(accsInfo.getInstances());
        deployment.setNotes(accsInfo.getNotes());
        deployment.setEnvironment(accsInfo.getEnvironment());
        deployment.setServices(services);

        return JsonConverter.createJsonFromJava(deployment, accsInfo.getMetaJsonPath(), ACCSConstants.METAFILES_DEPLOYMENT);
    }

    public static String zipPackage(ACCSInfo accsInfo) throws IOException {
        Path pathArchive = Paths.get(accsInfo.getOutputPath(), accsInfo.getArchiveName() + ".zip");
        Path pathJar = Paths.get(accsInfo.getOutputPath(), accsInfo.getArchiveName() + ".jar");
        Path pathManifest = Paths.get(accsInfo.getMetaJsonPath(), ACCSConstants.METAFILES_MANIFEST);

        createZipArchive(pathArchive, pathJar, pathManifest);

        return "ARCHIVE: " + pathArchive.toString() + ACCSConstants.LINE_SEPARATOR
                + "JAR: " + pathJar.toString() + ACCSConstants.LINE_SEPARATOR
                + "MANIFEST: " + pathManifest.toString();
    }

    private static void createZipArchive(Path archivePath, Path... targetFile) throws IOException {
        File accsArchive = archivePath.toFile();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(accsArchive))) {
            Arrays.stream(targetFile).forEach(s -> {
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(s.getFileName().toString()));
                    Files.copy(s, zipOutputStream);
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

    public static String listApplications(ACCSInfo accsInfo) throws IOException {
        String region = accsInfo.getRegion().equals("us") ? ACCSConstants.REST_API_DOMAIN_US : ACCSConstants.REST_API_DOMAIN_EMEA;
        String url = "https://" + region + ACCSConstants.REST_API_APAAS_PATH + "/" + accsInfo.getIdentityDomain();
        BasicNameValuePair[] headers = new BasicNameValuePair[1];
        headers[0] = new BasicNameValuePair(ACCSConstants.HEADER_X_ID_TENANT_NAME, accsInfo.getIdentityDomain());
        Credentials credUser = new UsernamePasswordCredentials(accsInfo.getUsername(), accsInfo.getPassword());

        String result = null;
        try {
            result = GetMethod.HttpGetMethod(url, headers, null, credUser);
        } catch (Exception e) {
            accsInfo.getLog().error(e);
        } finally {
            return result;
        }
    }

    public static String createApplication(ACCSInfo accsInfo) throws IOException {
        String region = accsInfo.getRegion().equals("us") ? ACCSConstants.REST_API_DOMAIN_US : ACCSConstants.REST_API_DOMAIN_EMEA;
        String url = "https://" + region + ACCSConstants.REST_API_APAAS_PATH + "/" + accsInfo.getIdentityDomain();
        BasicNameValuePair[] headers = new BasicNameValuePair[2];
        headers[0] = new BasicNameValuePair(ACCSConstants.HEADER_X_ID_TENANT_NAME, accsInfo.getIdentityDomain());
        headers[1] = new BasicNameValuePair(ACCSConstants.HEADER_CONTENT_TYPE, ACCSConstants.HEADER_CONTENT_TYPE_VALUE);

        StringBody stringBodyName = new StringBody(accsInfo.getAppName(), ContentType.MULTIPART_FORM_DATA);
        StringBody stringBodyRuntime = new StringBody(accsInfo.getRuntime(), ContentType.MULTIPART_FORM_DATA);
        FileBody fileBodyManifest = new FileBody(Paths.get(accsInfo.getMetaJsonPath(),
                ACCSConstants.METAFILES_MANIFEST).toFile(), ContentType.APPLICATION_JSON);
        FileBody fileBodyDeployment = new FileBody(Paths.get(accsInfo.getMetaJsonPath(),
                ACCSConstants.METAFILES_DEPLOYMENT).toFile(), ContentType.APPLICATION_JSON);
        StringBody stringBodyArchiveURL = new StringBody(accsInfo.getArchiveURL(), ContentType.MULTIPART_FORM_DATA);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        HttpEntity entity = multipartEntityBuilder
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addPart("name", stringBodyName)
                .addPart("runtime", stringBodyRuntime)
                .addPart("manifest", fileBodyManifest)
                .addPart("deployment", fileBodyDeployment)
                .addPart("archiveURL", stringBodyArchiveURL)
                .build();

/**
 ArrayList<NameValuePair> bodies = new ArrayList<NameValuePair>(5);
 bodies.add(new BasicNameValuePair("name", accsInfo.getAppName()));
 bodies.add(new BasicNameValuePair("runtime", accsInfo.getRuntime()));
 bodies.add(new BasicNameValuePair("manifest",
 Files.readAllLines(Paths.get(accsInfo.getMetaJsonPath(), "manifest.json"), StandardCharsets.UTF_8).stream().collect(Collectors.joining())));
 bodies.add(new BasicNameValuePair("deployment",
 Files.readAllLines(Paths.get(accsInfo.getMetaJsonPath(), "deployment.json"), StandardCharsets.UTF_8).stream().collect(Collectors.joining())));
 bodies.add(new BasicNameValuePair("archiveURL", accsInfo.getArchiveURL()));
 **/

        /**
         StringEntity body = new StringEntity(
         "name=" + accsInfo.getAppName() + "&"
         + "runtime=" + accsInfo.getRuntime() + "&"
         + "subscription=" + accsInfo.getSubscription() + "&"
         + "manifest="
         + Files.readAllLines(Paths.get(accsInfo.getMetaJsonPath(), "manifest.json"), StandardCharsets.UTF_8).get(0) + "&"
         + "deployment="
         + Files.readAllLines(Paths.get(accsInfo.getMetaJsonPath(), "deployment.json"), StandardCharsets.UTF_8).get(0) + "&"
         + "archiveURL=" + accsInfo.getArchiveURL()
         );

         accsInfo.getLog().debug("BODY: " + EntityUtils.toString(body));
         **/
        Credentials credUser = new UsernamePasswordCredentials(accsInfo.getUsername(), accsInfo.getPassword());

        String result = null;
        try {
//            result = PostMethod.HttpPostMethod(url, headers, new UrlEncodedFormEntity(bodies, StandardCharsets.UTF_8), credUser);
            result = PostMethod.HttpPostMethod(url, headers, entity, credUser);
        } catch (Exception e) {
            accsInfo.getLog().error(e);
        } finally {
            return result;
        }
    }

}
