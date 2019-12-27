package io.jooby.servlet;

import io.jooby.Context;
import io.jooby.Route;

import javax.annotation.Nonnull;
import javax.servlet.Servlet;

public class ServletHandler implements Route.Handler {

  private Servlet servlet;

  public ServletHandler(Servlet servlet) {
    this.servlet = servlet;
  }

  @Nonnull @Override public Object apply(@Nonnull Context ctx) throws Exception {
    servlet.service(new HttpServletRequestImpl(ctx), new HttpServletResponseImpl(ctx));
    return ctx;
  }
}
