/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.netty;

import io.jooby.Jooby;
import io.jooby.Server;
import io.jooby.ServerOptions;
import io.jooby.Sneaky;
import io.jooby.internal.netty.DefaultHeaders;
import io.jooby.internal.netty.NettyNative;
import io.jooby.internal.netty.NettyPipeline;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import javax.annotation.Nonnull;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Web server implementation using <a href="https://netty.io/">Netty</a>.
 *
 * @author edgar
 * @since 2.0.0
 */

public class Netty extends Server.Base {

  static {
    System.setProperty("io.netty.leakDetection.level",
        System.getProperty("io.netty.leakDetection.level", "disabled"));
  }

  private static final int DATE_INTERVAL = 1000;

  private static final int BACKLOG = 8192;

  private List<Jooby> applications = new ArrayList<>();

  private EventLoopGroup acceptor;

  private EventLoopGroup ioLoop;

  private DefaultEventExecutorGroup worker;

  private ServerOptions options = new ServerOptions()
      .setSingleLoop(false)
      .setServer("netty");

  @Override public Netty setOptions(@Nonnull ServerOptions options) {
    this.options = options
        .setSingleLoop(options.getSingleLoop(false));
    return this;
  }

  @Nonnull @Override public ServerOptions getOptions() {
    return options;
  }

  @Nonnull @Override public Server start(@Nonnull Jooby application) {
    try {
      applications.add(application);

      addShutdownHook();

      /** Worker: Application blocking code */
      worker = new DefaultEventExecutorGroup(options.getWorkerThreads(),
          new DefaultThreadFactory("application"));
      fireStart(applications, worker);

      /** Disk attributes: */
      String tmpdir = applications.get(0).getTmpdir().toString();
      DiskFileUpload.baseDirectory = tmpdir;
      DiskAttribute.baseDirectory = tmpdir;

      NettyNative provider = NettyNative.get(getClass().getClassLoader());

      /** Acceptor: Accepts connections */
      this.acceptor = provider.group("netty-acceptor", options.getIoThreads());

      if (options.getSingleLoop()) {
        this.ioLoop = this.acceptor;
      } else {
        /** IO: processing connections, parsing messages and doing engine's internal work */
        this.ioLoop = provider.group("netty-io", options.getIoThreads());
      }

      /** File data factory: */
      HttpDataFactory factory = new DefaultHttpDataFactory(options.getBufferSize());

      /** Bootstrap: */
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.option(ChannelOption.SO_BACKLOG, BACKLOG);
      bootstrap.option(ChannelOption.SO_REUSEADDR, true);

      Consumer<HttpHeaders> defaultHeaders = options.isDefaultHeaders()
          ? defaultHeaders(acceptor.next())
          : defaultResponseType();

      bootstrap.group(acceptor, ioLoop)
          .channel(provider.channel())
          .childHandler(
              new NettyPipeline(applications.get(0), factory, defaultHeaders, options.isGzip(),
                  options.getBufferSize(),
                  options.getMaxRequestSize()))
          .childOption(ChannelOption.SO_REUSEADDR, true)
          .childOption(ChannelOption.TCP_NODELAY, true);

      bootstrap.bind("0.0.0.0", options.getPort()).get();

      fireReady(applications);
    } catch (InterruptedException x) {
      throw Sneaky.propagate(x);
    } catch (ExecutionException x) {
      Throwable cause = x.getCause();
      if (cause instanceof BindException) {
        cause = new BindException("Address already in use: " + options.getPort());
      }
      throw Sneaky.propagate(cause);
    }
    return this;
  }

  @Nonnull @Override public Server stop() {
    fireStop(applications);
    applications = null;
    if (acceptor != null) {
      acceptor.shutdownGracefully();
      acceptor = null;
    }
    if (!options.getSingleLoop() && ioLoop != null) {
      ioLoop.shutdownGracefully();
      ioLoop = null;
    }
    if (worker != null) {
      worker.shutdownGracefully();
      worker = null;
    }
    return this;
  }

  private static DefaultHeaders defaultHeaders(ScheduledExecutorService executor) {
    DefaultHeaders headers = new DefaultHeaders();
    executor.scheduleWithFixedDelay(headers, DATE_INTERVAL, DATE_INTERVAL, TimeUnit.MILLISECONDS);
    return headers;
  }

  private static Consumer<HttpHeaders> defaultResponseType() {
    return headers -> headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
  }
}