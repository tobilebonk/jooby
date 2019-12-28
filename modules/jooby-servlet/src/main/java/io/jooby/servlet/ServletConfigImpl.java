/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;

public class ServletConfigImpl implements ServletConfig {

  private ServletContext sc;
  private String name;

  public ServletConfigImpl(ServletContext sc, String name) {
    this.sc = sc;
    this.name = name;
  }

  @Override public String getServletName() {
    return name;
  }

  @Override public ServletContext getServletContext() {
    return sc;
  }

  @Override public String getInitParameter(String name) {
    return sc.getInitParameter(name);
  }

  @Override public Enumeration<String> getInitParameterNames() {
    return sc.getInitParameterNames();
  }
}
