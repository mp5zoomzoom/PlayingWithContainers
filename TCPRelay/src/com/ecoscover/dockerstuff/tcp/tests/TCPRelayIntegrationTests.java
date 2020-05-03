package com.ecoscover.dockerstuff.tcp.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecoscover.dockerstuff.tcp.TCPRelay;

import static org.mockito.Mockito.*;
//import com.github.dockerjava.core.*;
//import com.github.dockerjava.core.command.*;
//import com.github.dockerjava.api.*;
//import com.github.dockerjava.api.model.Container;

public class TCPRelayIntegrationTests {

	static final String fname= "../Dockerfile";
	
//	static DockerClientBuilder m_dockerClientBuilder;
//	static DockerClient m_docker;
	
	@BeforeClass
	static public void setupClass()
	{
	//	m_dockerClientBuilder= DockerClientBuilder.getInstance("unix:///var/run/docker.sock");
	//    m_docker= m_dockerClientBuilder.build();
	//    List<Container> containers= m_docker.listContainersCmd().exec();
	    
	//    System.out.println("containers:");
	//    for(int i=0; i < containers.size(); i++){
	//        System.out.println(containers.get(i));
	//    }
	}
	
	@Test
	public void test() {
		TCPRelay test= mock(TCPRelay.class);
		
		when(test.returnnumber(anyInt())).thenReturn(43);
		
		assertEquals(test.returnnumber(0),43);
		
	}

}
