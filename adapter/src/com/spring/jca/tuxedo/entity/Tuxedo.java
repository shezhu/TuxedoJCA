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
