package io.jooby.servlet;

import io.jooby.Context;
import io.jooby.Session;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpServletRequestImpl implements HttpServletRequest {

  private Context ctx;

  public HttpServletRequestImpl(Context ctx) {
    this.ctx = ctx;
  }

  @Override public String getAuthType() {
    return null;
  }

  @Override public Cookie[] getCookies() {
    return ctx.cookieMap().entrySet().stream().map(c ->
        new Cookie(c.getKey(), c.getValue())
    ).toArray(Cookie[]::new);
  }

  @Override public long getDateHeader(String name) {
    return ctx.header(name).longValue(-1);
  }

  @Override public String getHeader(String name) {
    return ctx.header(name).valueOrNull();
  }

  @Override public Enumeration<String> getHeaders(String name) {
    return Collections.enumeration(ctx.header(name).toList());
  }

  @Override public Enumeration<String> getHeaderNames() {
    return Collections.enumeration(ctx.headerMap().keySet());
  }

  @Override public int getIntHeader(String name) {
    return ctx.header(name).intValue(-1);
  }

  @Override public String getMethod() {
    return ctx.getMethod();
  }

  @Override public String getPathInfo() {
    return ctx.getRequestPath();
  }

  @Override public String getPathTranslated() {
    return ctx.getRequestPath();
  }

  @Override public String getContextPath() {
    return ctx.getContextPath();
  }

  @Override public String getQueryString() {
    return ctx.queryString();
  }

  @Override public String getRemoteUser() {
    return null;
  }

  @Override public boolean isUserInRole(String role) {
    return false;
  }

  @Override public Principal getUserPrincipal() {
    return null;
  }

  @Override public String getRequestedSessionId() {
    return null;
  }

  @Override public String getRequestURI() {
    return ctx.getRequestPath();
  }

  @Override public StringBuffer getRequestURL() {
    return new StringBuffer(ctx.getRequestURL());
  }

  @Override public String getServletPath() {
    return ctx.getRequestPath();
  }

  @Override public HttpSession getSession(boolean create) {
    Session session = ctx.sessionOrNull();
    if (session == null && create) {
      return new HttpSessionImpl(ctx.session());
    }
    return session == null ? null : new HttpSessionImpl(session);
  }

  @Override public HttpSession getSession() {
    return new HttpSessionImpl(ctx.session());
  }

  @Override public String changeSessionId() {
    Session session = ctx.sessionOrNull();
    if (session == null) {
      throw new IllegalStateException("No HTTP session");
    }
    return session.renewId().getId();
  }

  @Override public boolean isRequestedSessionIdValid() {
    return false;
  }

  @Override public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  @Override public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  @Override public boolean isRequestedSessionIdFromUrl() {
    return false;
  }

  @Override public boolean authenticate(HttpServletResponse response)
      throws IOException, ServletException {
    return false;
  }

  @Override public void login(String username, String password) throws ServletException {

  }

  @Override public void logout() throws ServletException {

  }

  @Override public Collection<Part> getParts() throws IOException, ServletException {
    return ctx.files().stream().map(PartImpl::new).collect(Collectors.toList());
  }

  @Override public Part getPart(String name) throws IOException, ServletException {
    return null;
  }

  @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
      throws IOException, ServletException {
    return null;
  }

  @Override public Object getAttribute(String name) {
    return ctx.getAttributes().get(name);
  }

  @Override public Enumeration<String> getAttributeNames() {
    return Collections.enumeration(ctx.getAttributes().keySet());
  }

  @Override public String getCharacterEncoding() {
    return "utf-8";
  }

  @Override public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

  }

  @Override public int getContentLength() {
    return ctx.body().size();
  }

  @Override public long getContentLengthLong() {
    return ctx.body().size();
  }

  @Override public String getContentType() {
    return ctx.header("Content-Type").valueOrNull();
  }

  @Override public ServletInputStream getInputStream() throws IOException {
    return new ServletInputStreamImpl(ctx.body().stream());
  }

  @Override public String getParameter(String name) {
    return Stream.of(ctx.multipart(name).valueOrNull(), ctx.query(name).valueOrNull())
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }

  @Override public Enumeration<String> getParameterNames() {
    return Collections.enumeration(Stream
        .concat(ctx.multipartMap().keySet().stream(), Stream.of(ctx.queryMap().keySet().stream()))
        .map(Object::toString)
        .distinct()
        .collect(Collectors.toList()));
  }

  @Override public String[] getParameterValues(String name) {
    return Stream.concat(ctx.multipart(name).toList().stream(), ctx.query(name).toList().stream())
        .map(Objects::toString)
        .toArray(String[]::new);
  }

  @Override public Map<String, String[]> getParameterMap() {
    Map<String, String[]> map = new HashMap<>();
    Stream.concat(ctx.multipartMultimap().entrySet().stream(),
        ctx.queryMultimap().entrySet().stream())
        .forEach(e -> {
          map.put(e.getKey(), e.getValue().toArray(new String[0]));
        });
    return map;
  }

  @Override public String getProtocol() {
    return ctx.getProtocol();
  }

  @Override public String getScheme() {
    return ctx.getScheme();
  }

  @Override public String getServerName() {
    return ctx.getServerHost();
  }

  @Override public int getServerPort() {
    return ctx.getServerPort();
  }

  @Override public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(ctx.body().stream()));
  }

  @Override public String getRemoteAddr() {
    return ctx.getRemoteAddress();
  }

  @Override public String getRemoteHost() {
    return ctx.getHost();
  }

  @Override public void setAttribute(String name, Object o) {
    ctx.attribute(name, o);
  }

  @Override public void removeAttribute(String name) {
    ctx.getAttributes().remove(name);
  }

  @Override public Locale getLocale() {
    return ctx.header("Accept-Locale").toOptional()
        .map(Locale::forLanguageTag)
        .orElse(Locale.getDefault());
  }

  @Override public Enumeration<Locale> getLocales() {
    return Collections.enumeration(Collections.singletonList(getLocale()));
  }

  @Override public boolean isSecure() {
    return ctx.isSecure();
  }

  @Override public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  @Override public String getRealPath(String path) {
    return ctx.getRequestURL(path);
  }

  @Override public int getRemotePort() {
    return ctx.getPort();
  }

  @Override public String getLocalName() {
    return ctx.getServerHost();
  }

  @Override public String getLocalAddr() {
    return ctx.getServerHost();
  }

  @Override public int getLocalPort() {
    return ctx.getServerPort();
  }

  @Override public ServletContext getServletContext() {
    return new ServletContextImpl(ctx.getRouter());
  }

  @Override public AsyncContext startAsync() throws IllegalStateException {
    return null;
  }

  @Override public AsyncContext startAsync(ServletRequest servletRequest,
      ServletResponse servletResponse) throws IllegalStateException {
    return null;
  }

  @Override public boolean isAsyncStarted() {
    return false;
  }

  @Override public boolean isAsyncSupported() {
    return false;
  }

  @Override public AsyncContext getAsyncContext() {
    return null;
  }

  @Override public DispatcherType getDispatcherType() {
    return DispatcherType.REQUEST;
  }
}
