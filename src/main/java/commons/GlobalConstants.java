package commons;

import java.io.File;

public class GlobalConstants {
    private static GlobalConstants globalConstants;

    private GlobalConstants() {

    }

    public static synchronized GlobalConstants getGlobalConstants() {
        if (globalConstants == null)
            globalConstants = new GlobalConstants();
        return globalConstants;
    }

    public int getLongTimeout() {
        return longTimeout;
    }

    public int getShortTimeout() {
        return shortTimeout;
    }

    public String getPortalPageUrl() {
        return portalPageUrl;
    }

    public String getAdminPageUrl() {
        return adminPageUrl;
    }

    public String getOsName() {
        return osName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getUploadFilesType() {
        return uploadFilesType;
    }

    public String getUploadFilesFolder() {
        return uploadFilesFolder;
    }

    public String getReportNGScreenshot() {
        return reportNGScreenshot;
    }

    public String getDownloadFilesFolder() {
        return downloadFilesFolder;
    }

    public String getBrowserLogsFolder() {
        return browserLogsFolder;
    }

    private final int longTimeout = 30;
    private final int shortTimeout = 6;
    private final String portalPageUrl = "https://demo.nopcommerce.com/";
    private final String adminPageUrl = "https://admin-demo.nopcommerce.com/";
    private final String osName = System.getProperty("os.name");
    private final String projectPath = System.getProperty("user.dir");

    private final String uploadFilesType = "//input[@type='file']";
    private final String uploadFilesFolder = projectPath + File.separator + "uploadFiles" + File.separator;
    private final String reportNGScreenshot = projectPath + File.separator + "ReportNGImage" + File.separator;
    private final String downloadFilesFolder = projectPath + File.separator + "downloadFiles";
    private final String browserLogsFolder = projectPath + File.separator + "browserLogs";
}