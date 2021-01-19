package com.highspot.configuration;

import com.highspot.executer.BatchExecuter;
import com.highspot.executer.ContentUpdater;
import com.highspot.model.Content;
import com.highspot.reader.ChangesJsonSteamReader;
import com.highspot.reader.IReader;
import com.highspot.reader.MixJsonStreamReader;
import com.highspot.validation.FileValidator;
import com.highspot.writer.IWriter;
import com.highspot.writer.JsonStreamWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * Defines beans used for the program.
 */
public class AppConfig {

    @Bean
    public IReader<Content> jsonStreamReader() {
        return new MixJsonStreamReader();
    }

    @Bean
    public IReader<Content> changeJsonStreamReader() {
        return new ChangesJsonSteamReader();
    }

    @Bean
    public IWriter jsonStreamWriter() {
        return new JsonStreamWriter();
    }

    @Bean
    public FileValidator fileValidator() {
        return new FileValidator();
    }

    @Bean
    public BatchExecuter batchExecuter(@Qualifier("jsonStreamReader") final IReader jsonStreamReader,
                                       @Qualifier("changeJsonStreamReader") final IReader changeJsonStreamReader,
                                       @Qualifier("jsonStreamWriter") final IWriter jsonStreamWriter) {
        return new BatchExecuter(jsonStreamReader, changeJsonStreamReader, jsonStreamWriter);
    }

    @Bean
    public ContentUpdater contentUpdater() {
        return new ContentUpdater();
    }
}
