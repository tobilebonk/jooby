package io.jooby.vaadin;

import com.vaadin.flow.server.PwaRegistry;
import com.vaadin.flow.server.RouteRegistry;
import com.vaadin.flow.server.VaadinContext;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.theme.AbstractTheme;
import io.jooby.Extension;
import io.jooby.Jooby;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class VaadinModule extends VaadinService implements Extension {
  @Override public void install(@Nonnull Jooby application) throws Exception {
  }

  @Override protected RouteRegistry getRouteRegistry() {
    return null;
  }

  @Override protected PwaRegistry getPwaRegistry() {
    return null;
  }

  @Override public String getContextRootRelativePath(VaadinRequest request) {
    return null;
  }

  @Override public String getMimeType(String resourceName) {
    return null;
  }

  @Override protected boolean requestCanCreateSession(VaadinRequest request) {
    return false;
  }

  @Override public String getServiceName() {
    return null;
  }

  @Override public String getMainDivId(VaadinSession session,
      VaadinRequest request) {
    return null;
  }

  @Override public URL getStaticResource(String url) {
    return null;
  }

  @Override public URL getResource(String url, WebBrowser browser,
      AbstractTheme theme) {
    return null;
  }

  @Override public InputStream getResourceAsStream(String url,
      WebBrowser browser, AbstractTheme theme) {
    return null;
  }

  @Override public String resolveResource(String url, WebBrowser browser) {
    return null;
  }

  @Override public Optional<String> getThemedUrl(String url,
      WebBrowser browser, AbstractTheme theme) {
    return Optional.empty();
  }

  @Override protected VaadinContext constructVaadinContext() {
    return null;
  }
}
