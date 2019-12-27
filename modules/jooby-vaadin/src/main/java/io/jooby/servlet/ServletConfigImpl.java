package io.jooby.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;

public class ServletConfigImpl implements ServletConfig {
  @Override public String getServletName() {
    return null;
  }

  @Override public ServletContext getServletContext() {
    return null;
  }

  @Override public String getInitParameter(String name) {
    return null;
  }

  @Override public Enumeration<String> getInitParameterNames() {
    return null;
  }
}
