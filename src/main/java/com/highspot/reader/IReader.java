package com.highspot.reader;

import com.highspot.exception.ReadFailedException;

import java.io.Reader;

/**
 * Generic reader interface.
 * @param <T> Generic type to be read
 */
public interface IReader<T> {

    T read(final Reader in) throws ReadFailedException;
}
