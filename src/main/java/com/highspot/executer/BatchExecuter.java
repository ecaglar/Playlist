package com.highspot.executer;

import com.highspot.model.Content;
import com.highspot.reader.IReader;
import com.highspot.validation.FileValidator;
import com.highspot.writer.IWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Responsible for processing input and change files. First it reads the input file and creates the
 * interim data structures. After, it reads change file and directly applies to original structure.
 * Lastly, it writes the result to the output file.
 */
public class BatchExecuter {

  private final IReader<Content> jsonStreamReader;
  private final IReader<Content> changeJsonStreamReader;
  private final IWriter<Content> jsonStreamWriter;
  Logger LOG = LogManager.getLogger(BatchExecuter.class);
  @Autowired private FileValidator fileValidator;

  @Autowired private ContentUpdater contentUpdater;

  public BatchExecuter(
      final IReader<Content> jsonStreamReader,
      final IReader<Content> changeJsonStreamReader,
      final IWriter<Content> jsonStreamWriter) {
    this.jsonStreamReader = jsonStreamReader;
    this.changeJsonStreamReader = changeJsonStreamReader;
    this.jsonStreamWriter = jsonStreamWriter;
  }

  public void process(final String inputFile, final String changeFile, final String outputFile)
      throws IOException {
    Path inputPath = Paths.get(inputFile);
    Path changePath = Paths.get(changeFile);
    Path outputPath = Paths.get(outputFile);

    // check if files exist, regular file and readable.
    fileValidator.isExistsRegularReadable(inputPath);
    fileValidator.isExistsRegularReadable(changePath);

    Path outCreatedFilePath = Files.createFile(outputPath);
    LOG.info("Output file has been created [path]:" + outputPath);

    File f = inputPath.toFile();
    FileInputStream fis = new FileInputStream(f);
    InputStreamReader input = new InputStreamReader(fis);

    LOG.info("Mix input file read starting. [path]" + inputPath);

    Content mixContent = jsonStreamReader.read(input);

    LOG.info("Mix input file read completed. [path]" + inputPath);

    contentUpdater.setContent(mixContent);

    Reader change = new InputStreamReader(new FileInputStream(changePath.toFile()));
    LOG.info("Change file read starting. [path]" + changePath);
    changeJsonStreamReader.read(change);
    LOG.info("Change file read completed. [path]" + changePath);

    File outFile = outCreatedFilePath.toFile();
    FileOutputStream fos = new FileOutputStream(outFile);
    OutputStreamWriter output = new OutputStreamWriter(fos);
    LOG.info("Output file write starting. [path]" + outCreatedFilePath);
    jsonStreamWriter.writeFile(output, contentUpdater.getContent());
    LOG.info("Output file write completed. [path]" + outCreatedFilePath);
  }
}
