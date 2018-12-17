/**
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    Copyright 2014 Edgar Espina
 */
package io.jooby.internal.utow;

import io.jooby.Jooby;
import io.jooby.Router;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.List;

public class UtowMultiHandler implements HttpHandler {
  private final List<Jooby> routers;

  public UtowMultiHandler(List<Jooby> routers) {
    this.routers = routers;
  }

  @Override public void handleRequest(HttpServerExchange exchange) throws Exception {
    for (Jooby router : routers) {
      UtowContext context = new UtowContext(exchange, router.worker(), router.errorHandler(),
          router.tmpdir());
      Router.Match match = router.match(context);
      if (match.matches()) {
        match.execute(context);
        return;
      }
    }
  }
}