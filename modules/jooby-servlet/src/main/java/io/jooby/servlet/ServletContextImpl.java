/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import com.typesafe.config.Config;
import io.jooby.Jooby;
import io.jooby.MediaType;
import io.jooby.Router;
import io.jooby.SneakyThrows;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServletContextImpl implements ServletContext {
  private final Jooby application;
  private final Map<String, Servlet> servlets;

  public ServletContextImpl(Jooby application, Map<String, Servlet> servlets) {
    this.application = application;
    this.servlets = servlets;
  }

  @Override public String getContextPath() {
    return application.getContextPath();
  }

  @Override public ServletContext getContext(String uripath) {
    return null;
  }

  @Override public int getMajorVersion() {
    return 3;
  }

  @Override public int getMinorVersion() {
    return 0;
  }

  @Override public int getEffectiveMajorVersion() {
    return getMajorVersion();
  }

  @Override public int getEffectiveMinorVersion() {
    return getMinorVersion();
  }

  @Override public String getMimeType(String file) {
    return MediaType.byFile(file).getValue();
  }

  @Override public Set<String> getResourcePaths(String path) {
    try {
      Enumeration<URL> resources = application.getClassLoader().getResources(path);
      return Collections.list(resources).stream()
          .map(URL::getPath)
          .collect(Collectors.toSet());
    } catch (IOException x) {
      return Collections.emptySet();
    }
  }

  @Override public URL getResource(String path) throws MalformedURLException {
    return application.getClassLoader().getResource(path);
  }

  @Override public InputStream getResourceAsStream(String path) {
    return application.getClassLoader().getResourceAsStream(path);
  }

  @Override public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  @Override public RequestDispatcher getNamedDispatcher(String name) {
    return null;
  }

  @Override public Servlet getServlet(String name) throws ServletException {
    return servlets.get(name);
  }

  @Override public Enumeration<Servlet> getServlets() {
    return Collections.enumeration(servlets.values());
  }

  @Override public Enumeration<String> getServletNames() {
    return Collections.enumeration(servlets.keySet());
  }

  @Override public void log(String msg) {
    application.getLog().info(msg);
  }

  @Override public void log(Exception exception, String msg) {
    application.getLog().error(msg, exception);
  }

  @Override public void log(String message, Throwable throwable) {
    application.getLog().error(message, throwable);
  }

  @Override public String getRealPath(String path) {
    try {
      URL resource = getClassLoader().getResource(path);
      return resource.getProtocol().equals("file") ? Paths.get(resource.toURI()).toString() : null;
    } catch (URISyntaxException x) {
      throw SneakyThrows.propagate(x);
    }
  }

  @Override public String getServerInfo() {
    return application.getServerOptions().getServer();
  }

  @Override public String getInitParameter(String name) {
    return application.getEnvironment().getProperty("servlet." + name, null);
  }

  @Override public Enumeration<String> getInitParameterNames() {
    Config config = application.getConfig();
    if (config.hasPath("servlet")) {
      return Collections.enumeration(config.getConfig("servlet").root().keySet());
    }
    return Collections.emptyEnumeration();
  }

  @Override public boolean setInitParameter(String name, String value) {
    return false;
  }

  @Override public Object getAttribute(String name) {
    return application.getAttributes().get(name);
  }

  @Override public Enumeration<String> getAttributeNames() {
    return Collections.enumeration(application.getAttributes().keySet());
  }

  @Override public void setAttribute(String name, Object object) {
    application.attribute(name, object);
  }

  @Override public void removeAttribute(String name) {
    application.getAttributes().remove(name);
  }

  @Override public String getServletContextName() {
    return application.getName();
  }

  @Override public ServletRegistration.Dynamic addServlet(String servletName,
      String className) {
    return null;
  }

  @Override public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
    return null;
  }

  @Override public ServletRegistration.Dynamic addServlet(String servletName,
      Class<? extends Servlet> servletClass) {
    return null;
  }

  @Override public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
    return null;
  }

  @Override public ServletRegistration getServletRegistration(String servletName) {
    return null;
  }

  @Override public Map<String, ? extends ServletRegistration> getServletRegistrations() {
    return null;
  }

  @Override public FilterRegistration.Dynamic addFilter(String filterName,
      String className) {
    return null;
  }

  @Override public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
    return null;
  }

  @Override public FilterRegistration.Dynamic addFilter(String filterName,
      Class<? extends Filter> filterClass) {
    return null;
  }

  @Override public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
    return null;
  }

  @Override public FilterRegistration getFilterRegistration(String filterName) {
    return null;
  }

  @Override public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
    return null;
  }

  @Override public SessionCookieConfig getSessionCookieConfig() {
    return null;
  }

  @Override public void setSessionTrackingModes(
      Set<SessionTrackingMode> sessionTrackingModes) {

  }

  @Override public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
    return null;
  }

  @Override public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
    return null;
  }

  @Override public void addListener(String className) {

  }

  @Override public <T extends EventListener> void addListener(T t) {

  }

  @Override public void addListener(Class<? extends EventListener> listenerClass) {

  }

  @Override public <T extends EventListener> T createListener(Class<T> clazz)
      throws ServletException {
    return null;
  }

  @Override public JspConfigDescriptor getJspConfigDescriptor() {
    return null;
  }

  @Override public ClassLoader getClassLoader() {
    return application.getClassLoader();
  }

  @Override public void declareRoles(String... roleNames) {

  }

  @Override public String getVirtualServerName() {
    return null;
  }
}
