package com.highspot.validation;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Validation checks for files.
 */
public class FileValidator {

    public boolean isExistsRegularReadable(Path path) {
        return exists(path) && isReadable(path) && isRegularFile(path);
    }
    public boolean exists(Path path) {
        return Files.exists(path);
    }
    public boolean isReadable(Path path) {
        return Files.isReadable(path);
    }
    public boolean isRegularFile(Path path) {
        return Files.isRegularFile(path);
    }
}
