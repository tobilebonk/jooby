/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import io.jooby.FileUpload;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

public class PartImpl implements Part {
  private FileUpload upload;

  public PartImpl(FileUpload upload) {
    this.upload = upload;
  }

  @Override public InputStream getInputStream() throws IOException {
    return upload.stream();
  }

  @Override public String getContentType() {
    return upload.getContentType();
  }

  @Override public String getName() {
    return upload.getFileName();
  }

  @Override public String getSubmittedFileName() {
    return upload.getFileName();
  }

  @Override public long getSize() {
    return upload.getFileSize();
  }

  @Override public void write(String fileName) throws IOException {
    Files.copy(upload.path(), Paths.get(fileName));
  }

  @Override public void delete() throws IOException {
    upload.destroy();
  }

  @Override public String getHeader(String name) {
    return null;
  }

  @Override public Collection<String> getHeaders(String name) {
    return Collections.emptySet();
  }

  @Override public Collection<String> getHeaderNames() {
    return Collections.emptySet();
  }
}
