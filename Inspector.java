import java.lang.reflect.*;
import java.lang.Object;
import java.lang.Class;

public class Inspector 
{
	private Class inspectedClass;
	private Class inspectedSuperClass;
	
	private Constructor[] inspectedConstructors;
	private Method[] inspectedMethods;
	private Field[] inspectedFields;
	private Class[] inspectedInterfaces;
	
	
	public void inspect(Object obj, boolean recursive)
	{
		getClass(obj);
		getSuperClass(obj);
		getInterfaces(obj);
		getFields(obj);
		
		//printInspectedName(inspectedClass);
		//printInspectedName(inspectedSuperClass);
	}

	private void getConstructors(Object obj)
	{
		
	}
	
	private void getFields(Object obj)
	{
		inspectedFields = obj.getClass().getDeclaredFields();
	}
	
	private void getMethods(Object obj)
	{
		
	}
	
	private void getInterfaces(Object obj)
	{
		inspectedInterfaces = obj.getClass().getInterfaces();
	}
	
	private void getSuperClass(Object obj)
	{
		inspectedSuperClass = obj.getClass().getSuperclass();
	}
	
	private void getClass(Object obj)
	{
		inspectedClass = obj.getClass();
	}
	
	private void printInspectedName(Object inpsectedObject) 
	{
		System.out.println(inpsectedObject);
	}
	
	public Class GetInspectedClass()
	{
		return inspectedClass;
	}
	
	public Class GetInspectedSuperClass()
	{
		return inspectedSuperClass;
	}
	
	public Class[] GetInspectedInterfaces()
	{
		return inspectedInterfaces;
	}
	
	public Field[] GetInspectedFields()
	{
		return inspectedFields;
	}
}
