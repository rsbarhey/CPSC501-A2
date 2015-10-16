import static org.junit.Assert.*;
import org.junit.Test;

class Base extends Super
{
	private int integerField;
	private double doubleField;
	private String stringField;
	private int[] intArray;
	
	public Base()
	{
		integerField = 10;
		doubleField = 1.0;
		stringField = "test";
		intArray = new int[]{1, 2, 3, 4};
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
}

public class TestInpsector {
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
}
