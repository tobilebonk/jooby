/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import io.jooby.Extension;
import io.jooby.Jooby;
import io.jooby.servlet.ServletConfigImpl;

import javax.annotation.Nonnull;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import java.util.HashMap;
import java.util.Map;

public class ServletModule implements Extension {

  private Map<String, Servlet> servlets = new HashMap<>();

  public ServletModule addServlet(String name, Servlet servlet) {
    servlets.put(name, servlet);
    return this;
  }

  public ServletModule addServlet(Servlet servlet) {
    return addServlet(servlet.getClass().getSimpleName(), servlet);
  }

  @Override public void install(@Nonnull Jooby application) throws Exception {
    ServletContextImpl sc = new ServletContextImpl(application, servlets);

    for (Map.Entry<String, Servlet> entry : servlets.entrySet()) {
      ServletConfig servletConfig = new ServletConfigImpl(sc, entry.getKey());
      entry.getValue().init(servletConfig);
    }
  }
}
