package com.oracle.jp.shinyay.util;

public class ACCSConstants {

    private ACCSConstants() {
    }

    public static final String HEADER_X_STORAGE_USER = "X-Storage-User";
    public static final String HEADER_X_STORAGE_PASS = "X-Storage-Pass";
    public static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
    public static final String HEADER_X_STORAGE_URL = "X-Storage-Url";
    public static final String HEADER_X_ID_TENANT_NAME = "X-ID-TENANT-NAME";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_VALUE = "multipart/form-data";

    public static final String HTTP_STATUS_200 = "HTTP/1.1 200 OK";
    public static final String HTTP_STATUS_201 = "HTTP/1.1 201 Created";
    public static final String HTTP_STATUS_202 = "HTTP/1.1 202 Accepted";
    public static final String HTTP_STATUS_204 = "HTTP/1.1 204 No Content";
    public static final String HTTP_STATUS_401 = "HTTP/1.1 401 Unauthorized";
    public static final String HTTP_STATUS_404 = "HTTP/1.1 404 Not Found";

    public static final int HTTP_STATUS_CODE_OK = 200;
    public static final int HTTP_STATUS_CODE_CREATED = 201;
    public static final int HTTP_STATUS_CODE_ACCEPTED = 202;
    public static final int HTTP_STATUS_CODE_NO_CONTENT = 204;
    public static final int HTTP_STATUS_CODE_UNAUTHORIZED = 401;
    public static final int HTTP_STATUS_CODE_NOT_FOUND = 404;

    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String LINE_SEPARATOR_LINUX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String METAFILES_MANIFEST = "manifest.json";
    public static final String METAFILES_DEPLOYMENT = "deployment.json";

    public static final String REST_API_DOMAIN_US = "psm.us.oraclecloud.com";
    public static final String REST_API_DOMAIN_EMEA = "psm.europe.oraclecloud.com";
    public static final String REST_API_APAAS_PATH = "/paas/service/apaas/api/v1.1/apps";


}