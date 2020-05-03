package com.ecoscover.dockerstuff.tcp.tests;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.ecoscover.dockerstuff.tcp.TCPRelay;

@RunWith(JUnitParamsRunner.class)
public class TCPRelayTests {


	@Test
	@Parameters({
		"1, 1",
		"2, 2",
		"3, 3",
		"4, 3",
		"5, 5"
	})
	public void testParameterized(int input, int expected) 
	{

		TCPRelay uut= new TCPRelay();
		int result= uut.returnnumber(input);
		assertEquals("number doesn't match",expected,result);
	}
	
	@Test
	@Parameters({
		"1, 1, true",
		"2, 2, true",
		"3, 4, false",
		"4, 4, false",
		"5, 5, true"
	})
	public void testTwoParameters(int in1, int in2, boolean expected)
	{
		TCPRelay uut= new TCPRelay();
		
		boolean result= uut.isSame(in1, in2);
		
		assertEquals("isSame didn't return expected value",expected,result);
	}

}
