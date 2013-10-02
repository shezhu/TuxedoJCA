package com.spring.jca.tuxedo;

import java.net.URISyntaxException;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.spring.jca.tuxedo.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Netty {
	private static final int PORT = 6781;

	@Autowired
	GenericApplicationContext context;

	NettyJaxrsServer netty;

	ResteasyDeployment deployment;

	@Before
	public void setUp() {
		ResourceFactory tuxedoSRF = new SpringResourceFactory("tuxedo",
				context, com.spring.jca.tuxedo.entity.Tuxedo.class);

		deployment = new ResteasyDeployment();
		deployment.setResourceFactories(Arrays.asList(tuxedoSRF));

		netty = new NettyJaxrsServer();
		netty.setDeployment(deployment);
		netty.setPort(PORT);
		netty.setRootResourcePath("");
		netty.setSecurityDomain(null);
		netty.start();
	}

	@After
	public void tearDown() {
		// netty.stop();
	}

	@Test
	public void get() throws InterruptedException, URISyntaxException {
		MockHttpRequest request = MockHttpRequest
				.get("/resteasy/tuple/SRVCNM=levereraPerson,RFV_ANVNAMN=40042841,RFV_ILOGGAD=int:10,RFV_BATCH=int:1,FML_PERSONNUMMER=193801010269")
				.accept(Arrays
						.asList(new MediaType[] { MediaType.TEXT_PLAIN_TYPE }));

		MockHttpResponse response = new MockHttpResponse();
		deployment.getDispatcher().invoke(request, response);

		System.out.println(response.getContentAsString());
		System.out.println(response.getErrorMessage());
		Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
	}
}
