package io.jooby.servlet;

import io.jooby.FileUpload;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class PartImpl implements Part {
  private FileUpload upload;

  public PartImpl(FileUpload upload) {
    this.upload = upload;
  }

  @Override public InputStream getInputStream() throws IOException {
    return null;
  }

  @Override public String getContentType() {
    return null;
  }

  @Override public String getName() {
    return null;
  }

  @Override public String getSubmittedFileName() {
    return null;
  }

  @Override public long getSize() {
    return 0;
  }

  @Override public void write(String fileName) throws IOException {

  }

  @Override public void delete() throws IOException {

  }

  @Override public String getHeader(String name) {
    return null;
  }

  @Override public Collection<String> getHeaders(String name) {
    return null;
  }

  @Override public Collection<String> getHeaderNames() {
    return null;
  }
}
