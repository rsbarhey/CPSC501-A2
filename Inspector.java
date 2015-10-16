import java.lang.reflect.*;
import java.lang.Object;
import java.lang.Class;
import java.util.ArrayList;

public class Inspector 
{
	private Class inspectedClass;
	private Class inspectedSuperClass;
	
	private ArrayList<Constructor> inspectedConstructors;
	private ArrayList<Method> inspectedMethods;
	private ArrayList<Field> inspectedFields;
	private ArrayList<Class> inspectedInterfaces;
	
	
	
	public void inspect(Object obj, boolean recursive)
	{
		getClass(obj);
	}
	
	private void getConstructors(Object obj)
	{
		
	}
	
	private void getFileds(Object obj)
	{

	}
	
	private void getMethods(Object obj)
	{
		
	}
	
	private void getInterfaces(Object obj)
	{
		
	}
	
	private void getSuperClass(Object obj)
	{
		
	}
	
	private void getClass(Object obj)
	{
		Class c = obj.getClass();
		inspectedClass = c;
	}
	
	public Class GetInspectedClass()
	{
		return inspectedClass;
	}
}
