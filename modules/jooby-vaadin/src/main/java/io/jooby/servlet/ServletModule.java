package io.jooby.servlet;

import io.jooby.Extension;
import io.jooby.Jooby;
import io.jooby.servlet.ServletConfigImpl;

import javax.annotation.Nonnull;

public class ServletModule implements Extension {
  @Override public void install(@Nonnull Jooby application) throws Exception {
    ServletContextImpl sc = new ServletContextImpl(application);
  }
}
