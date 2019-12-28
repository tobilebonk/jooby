/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.servlet;

import io.jooby.Context;
import io.jooby.ServerOptions;
import io.jooby.StatusCode;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HttpServletResponseImpl implements HttpServletResponse {

  private Context ctx;

  public HttpServletResponseImpl(Context ctx) {
    this.ctx = ctx;
  }

  @Override public void addCookie(Cookie cookie) {
    ctx.setResponseCookie(toCookie(cookie));
  }

  private io.jooby.Cookie toCookie(Cookie cookie) {
    io.jooby.Cookie result = new io.jooby.Cookie(cookie.getName(), cookie.getValue());
    result.setMaxAge(cookie.getMaxAge());
    result.setPath(cookie.getPath());
    result.setSecure(cookie.getSecure());
    result.setHttpOnly(cookie.isHttpOnly());
    return result;
  }

  @Override public boolean containsHeader(String name) {
    return ctx.getResponseHeader(name) != null;
  }

  @Override public String encodeURL(String url) {
    return url;
  }

  @Override public String encodeRedirectURL(String url) {
    return url;
  }

  @Override public String encodeUrl(String url) {
    return url;
  }

  @Override public String encodeRedirectUrl(String url) {
    return url;
  }

  @Override public void sendError(int sc, String msg) throws IOException {
    ctx.send(StatusCode.valueOf(sc));
  }

  @Override public void sendError(int sc) throws IOException {
    ctx.send(StatusCode.valueOf(sc));
  }

  @Override public void sendRedirect(String location) throws IOException {
    ctx.sendRedirect(location);
  }

  @Override public void setDateHeader(String name, long date) {
    ctx.setResponseHeader(name, new Date(date));
  }

  @Override public void addDateHeader(String name, long date) {
    ctx.setResponseHeader(name, new Date(date));
  }

  @Override public void setHeader(String name, String value) {
    ctx.setResponseHeader(name, value);
  }

  @Override public void addHeader(String name, String value) {
    ctx.setResponseHeader(name, value);
  }

  @Override public void setIntHeader(String name, int value) {
    ctx.setResponseHeader(name, value);
  }

  @Override public void addIntHeader(String name, int value) {
    ctx.setResponseHeader(name, value);
  }

  @Override public void setStatus(int sc) {
    ctx.setResponseCode(sc);
  }

  @Override public void setStatus(int sc, String sm) {
    ctx.setResponseCode(sc);
  }

  @Override public int getStatus() {
    return ctx.getResponseCode().value();
  }

  @Override public String getHeader(String name) {
    return ctx.getResponseHeader(name);
  }

  @Override public Collection<String> getHeaders(String name) {
    String value = ctx.getResponseHeader(name);
    return value == null ? Collections.emptyList() : Collections.singletonList(value);
  }

  @Override public Collection<String> getHeaderNames() {
    return Collections.emptyList();
  }

  @Override public String getCharacterEncoding() {
    return "utf-8";
  }

  @Override public String getContentType() {
    return ctx.getResponseType().getValue();
  }

  @Override public ServletOutputStream getOutputStream() throws IOException {
    return new ServeletOutputStreamImpl(ctx.responseStream());
  }

  @Override public PrintWriter getWriter() throws IOException {
    return ctx.responseWriter();
  }

  @Override public void setCharacterEncoding(String charset) {

  }

  @Override public void setContentLength(int len) {
    ctx.setResponseLength(len);
  }

  @Override public void setContentLengthLong(long len) {
    ctx.setResponseLength(len);
  }

  @Override public void setContentType(String type) {
    ctx.setResponseType(type);
  }

  @Override public void setBufferSize(int size) {
  }

  @Override public int getBufferSize() {
    return ServerOptions._16KB;
  }

  @Override public void flushBuffer() throws IOException {

  }

  @Override public void resetBuffer() {

  }

  @Override public boolean isCommitted() {
    return ctx.isResponseStarted();
  }

  @Override public void reset() {

  }

  @Override public void setLocale(Locale loc) {

  }

  @Override public Locale getLocale() {
    return Locale.getDefault();
  }
}
