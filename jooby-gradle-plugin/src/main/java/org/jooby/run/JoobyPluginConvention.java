/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package org.jooby.run;

import java.util.List;

public class JoobyPluginConvention {
  private List<String> includes;

  private List<String> excludes;

  private String logLevel = "info";

  private boolean block = true;

  public List<String> getIncludes() {
    return includes;
  }

  public void setIncludes(final List<String> includes) {
    this.includes = includes;
  }

  public List<String> getExcludes() {
    return excludes;
  }

  public void setExcludes(final List<String> excludes) {
    this.excludes = excludes;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(final String logLevel) {
    this.logLevel = logLevel;
  }

  public boolean isBlock() {
    return block;
  }

  public void setBlock(final boolean block) {
    this.block = block;
  }

}
