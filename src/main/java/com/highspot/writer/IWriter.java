package com.highspot.writer;

import com.highspot.exception.WriteFailedException;

import java.io.Writer;

/**
 * Generic writer interface for output.
 * @param <T> Type for output.
 */
public interface IWriter<T> {

    void writeFile(Writer out, T content) throws WriteFailedException;
}
