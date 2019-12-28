/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import io.jooby.Context;
import io.jooby.Route;

import javax.annotation.Nonnull;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

public class ServletHandler implements Route.Handler {

  private ServletContext sc;
  private Servlet servlet;

  public ServletHandler(ServletContext sc, Servlet servlet) {
    this.sc = sc;
    this.servlet = servlet;
  }

  @Nonnull @Override public Object apply(@Nonnull Context ctx) throws Exception {
    servlet.service(new HttpServletRequestImpl(ctx, sc), new HttpServletResponseImpl(ctx));
    return ctx;
  }
}
