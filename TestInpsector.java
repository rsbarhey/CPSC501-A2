import static org.junit.Assert.*;
import org.junit.Test;

class Base
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

public class TestInpsector {
	private Inspector inspector = new Inspector();
	
	@Test
	public void TestGetClassName() 
	{
		Object obj = new Base();
		inspector.inspect(obj, false);
		assertEquals("Base", inspector.GetInspectedClass().getName());
	}
}
