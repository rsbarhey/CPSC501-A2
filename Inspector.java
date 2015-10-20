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
		getMethods(obj);
		getConstructors(obj);
		
		printInspectedName(inspectedClass, "Inspecting class: \t\t");
		printInspectedName(inspectedSuperClass, "Imidiate super class: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		printListOfInspectedNames(inspectedInterfaces, "Imidiate interfaces: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		printListOfInspectedNames(inspectedConstructors, "Declared constructors: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		printListOfInspectedNames(inspectedMethods, "Declared methods: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		printListOfInspectedNames(inspectedFields, "Declared fields: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println("===============================================================================================");
	}
	
	private void printInspectedName(Object inpsectedObject, String header) 
	{
		System.out.println(header + inpsectedObject);
	}
	
	private void printListOfInspectedNames(Object[] inspectedObjects, String header)
	{
		if(inspectedObjects.length == 0)
		{
			System.out.println(header + "None");
		}
		else
		{
			System.out.printf(header + "%d \n", inspectedObjects.length);
			for(int i = 0; i<inspectedObjects.length; i++)
			{
				printInspectedName(inspectedObjects[i], "");
			}
		}
	}

	private void getConstructors(Object obj)
	{
		inspectedConstructors = obj.getClass().getConstructors();
	}
	
	private void getFields(Object obj)
	{
		inspectedFields = obj.getClass().getDeclaredFields();
	}
	
	private void getMethods(Object obj)
	{
		inspectedMethods = obj.getClass().getDeclaredMethods();
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
	
	public Method[] GetInspectedMethods()
	{
		return inspectedMethods;
	}
	
	public Constructor[] GetInspectedConstructors()
	{
		return inspectedConstructors;
	}
}
