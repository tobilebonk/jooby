/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

public class ServeletOutputStreamImpl extends ServletOutputStream {
  private final OutputStream output;

  public ServeletOutputStreamImpl(OutputStream output) {
    this.output = output;
  }

  @Override public boolean isReady() {
    return true;
  }

  @Override public void setWriteListener(WriteListener writeListener) {

  }

  @Override public void write(byte[] b) throws IOException {
    output.write(b);
  }

  @Override public void write(byte[] b, int off, int len) throws IOException {
    output.write(b, off, len);
  }

  @Override public void write(int b) throws IOException {
    output.write(b);
  }
}
