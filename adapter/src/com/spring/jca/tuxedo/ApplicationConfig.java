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
package com.spring.jca.tuxedo;

import javax.naming.NamingException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ManagedConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.cci.connection.CciLocalTransactionManager;
import org.springframework.jca.cci.core.CciTemplate;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.jca.support.ResourceAdapterFactoryBean;
import org.springframework.jca.support.SimpleBootstrapContext;
import org.springframework.jca.work.SimpleTaskWorkManager;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import com.spring.jca.tuxedo.entity.Tuxedo;
import com.spring.jca.tuxedo.entity.TuxedoImpl;

import com.oracle.tuxedo.adapter.TuxedoClientSideResourceAdapter;
import com.oracle.tuxedo.adapter.TuxedoResourceAdapter;
import com.oracle.tuxedo.adapter.spi.TuxedoFBCManagedConnectionFactory;
import com.oracle.tuxedo.adapter.spi.TuxedoManagedConnectionFactory;

@Configuration
public class ApplicationConfig {
	ResourceAdapterFactoryBean tuxedoCSResourceAdapter() {
		// http://stackoverflow.com/questions/5682732/how-does-an-application-that-uses-springs-simplenamingcontextbuilder-know-to-se
		try {
			SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (NamingException e) {
			System.out.println("Simple Naming context builder error: "
					+ e.getMessage());
		}

		TuxedoClientSideResourceAdapter ra = new TuxedoClientSideResourceAdapter();
		ra.setTraceLevel("100000");

		ra.setDebugAdapter(true);
		ra.setDebugBuffer(true);
		ra.setDebugJatmi(true);
		ra.setDebugRouting(true);
		ra.setDebugSession(true);
		ra.setDebugXa(true);
		ra.setDebugConfig(true);
		ra.setDebugPdu(true);
		ra.setDebugPerf(true);
		ra.setDebugSec(true);
		ra.setDebugNet(true);

		ra.setXaAffinity("true");
		ra.setAutoTran(true);

		ra.setSpCredentialPolicy("LOCAL");
		ra.setRapAllowAnonymous(false);

		ra.setLocalAccessPointSpec("//INV000000121176.ads.sfa.se:7001/domainId=INV000000121176");
		ra.setRemoteAccessPointSpec("//vsgtu702.sfa.se:47022/domainId=tr703tu");

		ra.setImpResourceName("levereraPerson");
		
		ra.setFieldTable32Classes("se.fk.silk.tuxedo.SampleFldTbl");

		ResourceAdapterFactoryBean fc = new ResourceAdapterFactoryBean();
		fc.setResourceAdapter(ra);

		SimpleTaskWorkManager stwm = new SimpleTaskWorkManager();
		fc.setWorkManager(stwm);
		fc.setBootstrapContext(new SimpleBootstrapContext(stwm));

		return fc;
	}

	@Bean
	public ResourceAdapterFactoryBean tuxedoResourceAdapter() {
		try {
			SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (NamingException e) {
			System.out.println("Simple Naming context builder error: "
					+ e.getMessage());
		}
		
		TuxedoResourceAdapter ra = new TuxedoResourceAdapter();
		ra.setTraceLevel("100000");

		ra.setDebugAdapter(true);
		ra.setDebugBuffer(true);
		ra.setDebugJatmi(true);
		ra.setDebugRouting(true);
		ra.setDebugSession(true);
		ra.setDebugXa(true);
		ra.setDebugConfig(true);
		ra.setDebugPdu(true);
		ra.setDebugPerf(true);
		ra.setDebugSec(true);
		ra.setDebugNet(true);

		ra.setXaAffinity("true");

		ra.setDmconfig("C:\\Users\\40042466\\workspace\\Silk Tuxedo\\configuration\\runtime\\dmconfig.xml");

		ResourceAdapterFactoryBean fc = new ResourceAdapterFactoryBean();
		fc.setResourceAdapter(ra);

		SimpleTaskWorkManager stwm = new SimpleTaskWorkManager();
		fc.setWorkManager(stwm);
		fc.setBootstrapContext(new SimpleBootstrapContext(stwm));

		return fc;
	}


	@Bean
	public ManagedConnectionFactory tuxedoManagedConnectionFactory() {
		TuxedoManagedConnectionFactory tmcf = new TuxedoManagedConnectionFactory();

		return tmcf;
	}

	public ManagedConnectionFactory tuxedoManagedFBConnectionFactory() {
		TuxedoFBCManagedConnectionFactory tmcf = new TuxedoFBCManagedConnectionFactory();
		tmcf.setRemoteAccessPointSpec("//vsgktdp010:30100/domainId=TDP010K2");

		return tmcf;
	}

	@Bean
	public LocalConnectionFactoryBean tuxedoConnectionFactoryBean() {
		LocalConnectionFactoryBean lcfb = new LocalConnectionFactoryBean();
		lcfb.setManagedConnectionFactory(tuxedoManagedConnectionFactory());

		return lcfb;
	}

	@Bean
	public ConnectionFactory tuxedoConnectionFactory() {
		return (ConnectionFactory) tuxedoConnectionFactoryBean().getObject();
	}

	@Bean
	public CciTemplate tuxedoCciTemplate() {
		CciTemplate ct = new CciTemplate();
		ct.setConnectionFactory(tuxedoConnectionFactory());

		return ct;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		CciLocalTransactionManager cciTm = new CciLocalTransactionManager();
		cciTm.setConnectionFactory(tuxedoConnectionFactory());
		
		return cciTm;
	}
	
	@Bean
	public Tuxedo tuxedo() {
		return new TuxedoImpl();
	}
}
