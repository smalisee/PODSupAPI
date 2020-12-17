package com.amos.podsupapi.syn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.util.IOUtils;
import com.amos.podsupapi.component.aws.AWSFileTransferComponent;
import com.amos.podsupapi.config.AppConfig;

@Component
public class SynchronizeImage {
  private static final Logger LOGGER = LogManager.getLogger(SynchronizeImage.class);

  @Autowired
  private AWSFileTransferComponent awsFileTranfer;

  @Scheduled(cron = "0 0 * * * *")
  public void synchronize() {
    String imagePath = AppConfig.getApiDeliveryBasePathImages();
    if (AppConfig.isCronSyncImageAwsEnable()) {
      LOGGER.info("SynchronizeImage Start");
      walk(imagePath);
    } else {
      LOGGER.info("SynchronizeImage is not enable");
    }

  }

  private void walk(String path) {
    File root = new File(path);
    File[] list = root.listFiles();

    if (list == null)
      return;

    for (File f : list) {
      if (f.isDirectory()) {
        walk(f.getAbsolutePath());
      } else {
        String key = f.getAbsolutePath().substring(1, f.getAbsolutePath().length());

        try (FileInputStream inputStream = new FileInputStream(f)) {
          awsFileTranfer.putObject(key, IOUtils.toByteArray(inputStream));
          java.nio.file.Files.delete(f.toPath());
          LOGGER.info(String.format("Synchronize Key:%s ,Filename:%s ", key, f.getName()));
        } catch (IOException ex) {
          LOGGER.error(String.format("Synchronize image error %s ", ex.getMessage()), ex);
        }

      }
    }

  }
}
