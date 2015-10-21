import static org.junit.Assert.*;

import org.junit.Test;

class Base extends Super implements DoSomething
{
	private int integerField;
	private double doubleField;
	private String stringField;
	private int[] intArray;
	private Super superField = new Super();
	
	public Base()
	{
		integerField = 10;
		doubleField = 1.0;
		stringField = "test";
		intArray = new int[]{1, 2, 3, 4};
	}

	public void DoSomething() 
	{
		stringField = DoSomething.interfaceString + "Base is doing something";
	}
	
	public String methodFromBase(String string, int[] intArray) throws Exception
	{
		return "";
	}
}

class Super
{
	private int superInteger;
	private double superDouble;
	private boolean superBool;
	
	public Super()
	{
		superInteger = -10;
		superDouble = -1.0;
		superBool = false;
	}
	
	public Super(int integerParam, double doubleParam, boolean boolParam)
	{
		superInteger = integerParam;
		superDouble = doubleParam;
		superBool = boolParam;
	}
	
	public Super(Super para) throws Exception
	{
		superInteger = para.superInteger;
		superDouble = para.superDouble;
		superBool = para.superBool;
	}
}

interface DoSomething
{
	static final String interfaceString = "IDoSomething: ";
	
	public void DoSomething();
}

public class TestInspector {
	
	private Inspector inspector = new Inspector();
	
	@Test
	public void TestGetClass() 
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals("Base", inspector.GetInspectedClass().getName());
		assertEquals(Base.class, inspector.GetInspectedClass());
	}
	
	@Test
	public void TestGetSuperClass()
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals("Super", inspector.GetInspectedSuperClass().getName());
		assertEquals(Super.class, inspector.GetInspectedSuperClass());
		
		obj = new Super();
		inspector.inspect(obj, false);
		//Every class in java inherits Object directly(in case of Super) or indirectly(in case of Base)
		assertEquals(Object.class, inspector.GetInspectedSuperClass());
	}
	
	@Test
	public void TestGetInterfaces()
	{
		Object obj = new Super();
		inspector.inspect(obj, false);
		assertEquals(0, inspector.GetInspectedInterfaces().length);
		
		obj = new Base();
		inspector.inspect(obj, false);
		assertEquals(1, inspector.GetInspectedInterfaces().length);
		assertEquals("DoSomething", inspector.GetInspectedInterfaces()[0].getName());
		assertEquals(DoSomething.class, inspector.GetInspectedInterfaces()[0]);	
	}
	
	@Test
	public void TestGetFields()
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals(5, inspector.GetInspectedFields().length);
		assertEquals("integerField", inspector.GetInspectedFields()[0].getName());
		assertEquals("doubleField", inspector.GetInspectedFields()[1].getName());
		assertEquals("stringField", inspector.GetInspectedFields()[2].getName());
		assertEquals("intArray", inspector.GetInspectedFields()[3].getName());
		
		obj = new Super();
		inspector.inspect(obj, false);
		assertEquals(3, inspector.GetInspectedFields().length);
	}
	
	@Test
	public void TestGetMethods()
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals(2, inspector.GetInspectedMethods().length);
		assertEquals("DoSomething", inspector.GetInspectedMethods()[0].getName());
		
		obj = new Super();
		inspector.inspect(obj, false);
		assertEquals(0, inspector.GetInspectedMethods().length);
	}
	
	@Test
	public void TestGetConstructors()
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals(1, inspector.GetInspectedConstructors().length);
		assertEquals("Base", inspector.GetInspectedConstructors()[0].getName());
		assertEquals(0, inspector.GetInspectedConstructors()[0].getParameterTypes().length);
		
		obj = new Super();
		inspector.inspect(obj, false);
		assertEquals(3, inspector.GetInspectedConstructors().length);
		assertEquals("Super", inspector.GetInspectedConstructors()[1].getName());
		assertEquals(3, inspector.GetInspectedConstructors()[1].getParameterTypes().length);
	}
}
