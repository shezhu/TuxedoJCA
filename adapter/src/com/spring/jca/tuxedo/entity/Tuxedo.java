/**
   Copyright 2013 Matthew Young

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.spring.jca.tuxedo.entity;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

@Path("/resteasy")
@Consumes({ MediaType.TEXT_PLAIN })
@Produces({ MediaType.TEXT_PLAIN })
public interface Tuxedo {

	@GET
	@Path("/tuple/{buffer}")
	public String execute(@PathParam("buffer") String request);
}
