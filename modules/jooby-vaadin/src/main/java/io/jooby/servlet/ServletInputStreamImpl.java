package io.jooby.servlet;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServletInputStreamImpl extends ServletInputStream {
  private final InputStream input;

  public ServletInputStreamImpl(InputStream input) {
    this.input = input;
  }

  @Override public boolean isFinished() {
    return true;
  }

  @Override public boolean isReady() {
    return true;
  }

  @Override public void setReadListener(ReadListener readListener) {

  }

  @Override public int read(@NotNull byte[] b) throws IOException {
    return input.read(b);
  }

  @Override public int read(@NotNull byte[] b, int off, int len) throws IOException {
    return input.read(b, off, len);
  }

  @Override public synchronized void reset() throws IOException {
    input.reset();
  }

  @Override public boolean markSupported() {
    return input.markSupported();
  }

  @Override public synchronized void mark(int readlimit) {
    input.mark(readlimit);
  }

  @Override public int read() throws IOException {
    return input.read();
  }
}
