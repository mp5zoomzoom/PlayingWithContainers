package com.ecoscover.dockerstuff.tcp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ecoscover.dockerstuff.tcp.TCPClientEndpoint;

@RunWith(Parameterized.class)
public class TCPClientEndpointTests
{
	int input;
	int expected;
	
	public TCPClientEndpointTests(int input, int expected)
	{
		this.input= input;
		this.expected= expected;
	}
	
	@Parameters
	public static Collection<Object[]> data() throws Throwable
	{
		return Arrays.asList(new Object[][]
		{
			{1,1},
			{2,2},
			{3,4},
			{6,6},
			{7,7},
			{8,3},
			{9,9},
			{10,10},
			{13,12},
			{14,14},
			{18,17},
			{19,19},
			{20,20},
			{21,21},
			{22,22}			
		});
	}
	@Test
	public void parameterizedTest() throws Throwable
	{
		//setup
		TCPClientEndpoint uut= new TCPClientEndpoint();
		
		//act
		int retval= uut.returnnumber(input);
		assertEquals("numbers don't match",expected,retval);
	}

}
