package com.highspot;

import com.highspot.configuration.AppConfig;
import com.highspot.exception.ReadFailedException;
import com.highspot.exception.WriteFailedException;
import com.highspot.executer.BatchExecuter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
@Import({AppConfig.class})
public class PlaylistApplication implements ApplicationRunner {

  Logger LOG = LogManager.getLogger(PlaylistApplication.class);

  @Autowired ConfigurableApplicationContext applicationContext;

  public static void main(String[] args) {

    SpringApplication.run(PlaylistApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    String inputMixFile = null;
    String inputChangeFile = null;
    String outputFile = null;

    if (args.containsOption("input-file")) {
      inputMixFile = args.getOptionValues("input-file").get(0);
    } else {
      LOG.error("Missing --input-file argument! Closing the application");
      applicationContext.close();
    }
    if (args.containsOption("change-file-name")) {
      inputChangeFile = args.getOptionValues("change-file-name").get(0);
    } else {
      LOG.error("Missing change-file-name argument! Closing the application");
      applicationContext.close();
    }
    if (args.containsOption("output-file-name")) {
      outputFile = args.getOptionValues("output-file-name").get(0);
    } else {
      LOG.error("Missing output-file-name argument! Closing the application");
      applicationContext.close();
    }

    BatchExecuter batchExecuter = applicationContext.getBean(BatchExecuter.class);
    try {
      batchExecuter.process(inputMixFile, inputChangeFile, outputFile);
    } catch (ReadFailedException e) {
      LOG.error("Error while reading files: " + e.getMessage());
      applicationContext.close();
    } catch (WriteFailedException e) {
      LOG.error("Error while writing output file: " + e.getMessage());
      applicationContext.close();
    } catch (IOException e) {
      LOG.error("File operation error: " + e.getMessage());
      applicationContext.close();
    }
  }
}
