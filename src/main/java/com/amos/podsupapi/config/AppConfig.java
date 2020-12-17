package com.amos.podsupapi.config;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConfig {

  private static String FILE_CONFIG = "/appstore/mos/24WHAPI/config/config.properties";

  private static final Logger logger = LogManager.getLogger(AppConfig.class);

  private AppConfig() {
  }

  // Properties
  // appResource
  private static String apiDeliveryBasePathImages;
  private static String apiURLGetLocationStoreExternalAPI;
  private static String apiURLGetNearbyStoreExternalAPI;

  // Location
  private static String locationWarehouseDefaultLatitude;
  private static String locationWarehouseDefaultLongitude;

  // JWT
  private static String authorization;
  private static String jwtSecretHMAC256;
  private static String jwtDefaultTokenExpireHours;

  // LDAP
  private static String ldapUsername;
  private static String ldapPassword;
  private static String ldapServerPrimary;
  private static String ldapServerSecondary;
  private static String ldapServerThridary;
  private static String ldapDomain;
  private static String ldapDn;

  private static String awsProxyHost;
  private static Integer awsProxyPort;
  private static String awsAccessKey;
  private static String awsSecretKey;
  private static String awsBucketName;

  private static Boolean cronSyncImageAwsEnable;

  static {
    try {
      loadConfig();
    } catch (Exception e) {
      logger.error("LoadConfig Error !!!!!", e);
    }
  }

  public static void loadConfig() throws Exception {

    // To call on Local Resource use => AppConfig.class.getClassLoader().getResource("config.properties").getFile();
    // FILE_CONFIG = AppConfig.class.getClassLoader().getResource("config.properties").getFile();
    try (FileInputStream configInputStream = new FileInputStream(FILE_CONFIG)) {

      Properties prop = new Properties();
      prop.load(configInputStream);

      // set property value
      AppConfig.locationWarehouseDefaultLatitude = prop.getProperty("api.location.warehouse.default.latitude");
      AppConfig.locationWarehouseDefaultLongitude = prop.getProperty("api.location.warehouse.default.longitude");

      AppConfig.apiURLGetLocationStoreExternalAPI = prop.getProperty("api.url.external.api.getstorelocation");
      AppConfig.apiURLGetNearbyStoreExternalAPI = prop.getProperty("api.url.external.api.getnearbystore");

      AppConfig.apiDeliveryBasePathImages = prop.getProperty("api.delivery.image.path");
      AppConfig.authorization = prop.getProperty("header.auth");
      AppConfig.jwtSecretHMAC256 = prop.getProperty("jwt.token.secret.HMAC256");
      AppConfig.jwtDefaultTokenExpireHours = prop.getProperty("jwt.token.expire.hours");

      AppConfig.ldapUsername = prop.getProperty("ldap.username");
      AppConfig.ldapPassword = prop.getProperty("ldap.password");
      AppConfig.ldapServerPrimary = prop.getProperty("ldap.server.primary");
      AppConfig.ldapServerSecondary = prop.getProperty("ldap.server.secondary");
      AppConfig.ldapServerThridary = prop.getProperty("ldap.server.thirdary");
      AppConfig.ldapDomain = prop.getProperty("ldap.domain");
      AppConfig.ldapDn = prop.getProperty("ldap.dn");

      AppConfig.awsProxyHost = prop.getProperty("aws.s3.proxy.host");
      AppConfig.awsProxyPort = Integer.parseInt(prop.getProperty("aws.s3.proxy.port"));
      AppConfig.awsAccessKey = prop.getProperty("aws.s3.accesskey");
      AppConfig.awsSecretKey = prop.getProperty("aws.s3.secretkey");
      AppConfig.awsBucketName = prop.getProperty("aws.s3.bucket");

      AppConfig.cronSyncImageAwsEnable = Boolean.parseBoolean(prop.getProperty("cron.sync.image.aws.enable"));
    }
  }

  public static String getLocationWarehouseDefaultLatitude() {
    return locationWarehouseDefaultLatitude;
  }

  public static String getLocationWarehouseDefaultLongitude() {
    return locationWarehouseDefaultLongitude;
  }

  public static String getAuthorization() {
    return authorization;
  }

  public static String getSecretHMAC256() {
    return jwtSecretHMAC256;
  }

  public static Integer getDefaultTokenExpireHours() {
    return Integer.parseInt(jwtDefaultTokenExpireHours);
  }

  public static String getLdapUsername() {
    return ldapUsername;
  }

  public static String getLdapPassword() {
    return ldapPassword;
  }

  public static String getLdapServerPrimary() {
    return ldapServerPrimary;
  }

  public static String getLdapServerSecondary() {
    return ldapServerSecondary;
  }

  public static String getLdapServerThridary() {
    return ldapServerThridary;
  }

  public static String getLdapDomain() {
    return ldapDomain;
  }

  public static String getLdapDn() {
    return ldapDn;
  }

  public static String getApiDeliveryBasePathImages() {
    return apiDeliveryBasePathImages;
  }

  public static String getApiURLGetLocationStoreExternalAPI() {
    return apiURLGetLocationStoreExternalAPI;
  }

  public static String getApiURLGetNearbyStoreExternalAPI() {
    return apiURLGetNearbyStoreExternalAPI;
  }

  public static Boolean isCronSyncImageAwsEnable() {
    return cronSyncImageAwsEnable;
  }

  public static String getAwsProxyHost() {
    return awsProxyHost;
  }

  public static Integer getAwsProxyPort() {
    return awsProxyPort;
  }

  public static String getAwsAccessKey() {
    return awsAccessKey;
  }

  public static String getAwsSecretKey() {
    return awsSecretKey;
  }

  public static String getAwsBucketName() {
    return awsBucketName;
  }

  private static final String CURRENT_CONFIG_LOG_FORMAT_STR = "%s = %s;" + System.lineSeparator();
  private static final String CURRENT_CONFIG_LOG_FORMAT_NUMBER = "%s = %d;" + System.lineSeparator();

  public static String getCurrentConfig() {
    String current = "Current config";
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "BasePathImages", getApiDeliveryBasePathImages());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "BasePathImages", getAuthorization());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "DefaultTokenExpireHours", getDefaultTokenExpireHours());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapDn", getLdapDn());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapDomain", getLdapDomain());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapServerPrimary", getLdapServerPrimary());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapServerSecondary", getLdapServerSecondary());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapServerThridary", getLdapServerThridary());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LdapUsername", getLdapUsername());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LocationWarehouseDefaultLatitude",
        getLocationWarehouseDefaultLatitude());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "LocationWarehouseDefaultLongitude",
        getLocationWarehouseDefaultLongitude());

    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "awsProxyHost", getAwsProxyHost());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_NUMBER, "awsProxyPort", getAwsProxyPort());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "AwsAccessKey", getAwsAccessKey());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "AwsSecretKey", getAwsSecretKey());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "AwsBucketName", getAwsBucketName());
    current += String.format(CURRENT_CONFIG_LOG_FORMAT_STR, "CronSyncImageAwsEnable", isCronSyncImageAwsEnable());
    return current;

  }

}
