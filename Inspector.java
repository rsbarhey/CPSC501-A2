import java.lang.reflect.*;
import java.lang.Object;
import java.lang.Class;
import java.util.Arrays;

public class Inspector
{
	private Class inspectedClass;
	private Class inspectedSuperClass;

	private Constructor[] inspectedConstructors;
	private Method[] inspectedMethods;
	private Field[] inspectedFields;
	private Class[] inspectedInterfaces;
	
	final Class<?>[] PRIMITIVE_WRAPPERS = {Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, String.class};

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
		
		//Traversing the hierarchy
		System.out.println("***********************************************************************************************");
		traverseSuperClass(inspectedSuperClass, inspectedClass);
		System.out.println("***********************************************************************************************");
		System.out.println("_______________________________________________________________________________________________");
		traverseInterfaces(inspectedInterfaces, inspectedClass);
		System.out.println("_______________________________________________________________________________________________");
		
		//Printing values
		printFieldsValues(inspectedFields, obj, recursive);
		System.out.println("===============================================================================================");
	}

	private void printInspectedName(Object inpsectedObject, String header)
	{
		System.out.println(header + inpsectedObject);
	}

	private void printListOfInspectedNames(Object[] inspectedObjects, String header) 
	{
		if (inspectedObjects.length == 0)
		{
			System.out.println(header + "None");
		} 
		else
		{
			System.out.printf(header + "%d \n", inspectedObjects.length);
			for (int i = 0; i < inspectedObjects.length; i++) {
				printInspectedName(inspectedObjects[i], "");
			}
		}
	}

	private void traverseSuperClass(Class superClass, Class extendingClass)
	{
		if (superClass.equals(Object.class))
		{
			return;
		}
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println(extendingClass.getName() + " extends class " + superClass.getName());
		printListOfInspectedNames(superClass.getDeclaredMethods(), "Methods: \t\t");
		System.out.println("");
		printListOfInspectedNames(superClass.getDeclaredFields(), "Fields: \t\t");
		System.out.println("-----------------------------------------------------------------------------------------------");
		traverseInterfaces(superClass.getInterfaces(), superClass);
		traverseSuperClass(superClass.getSuperclass(), superClass);
	}

	private void traverseInterfaces(Class[] interfaces, Class extendingClass)
	{
		if (interfaces.length == 0)
		{
			return;
		}
		for (int i = 0; i < interfaces.length; i++)
		{
			System.out.println("-----------------------------------------------------------------------------------------------");
			System.out.println(extendingClass.getName() +" extends interface " + interfaces[i].getName());
			printListOfInspectedNames(interfaces[i].getDeclaredMethods(), "Implemented methods: \t\t");
			System.out.println("");
			printListOfInspectedNames(interfaces[i].getDeclaredFields(), "Constant fields: \t\t");
			System.out.println("-----------------------------------------------------------------------------------------------");
			traverseInterfaces(interfaces[i].getInterfaces(), interfaces[i]);
		}
	}
	
	private void printFieldsValues(Field[] fields, Object obj, boolean recursive)
	{
		for(int i = 0; i<fields.length; i++)
		{
			fields[i].setAccessible(true);
			try 
			{
				Object value = fields[i].get(obj);
				
				if(value == null)
				{
					printInspectedName(value, fields[i].getName() + " = ");
				}
				
				else if(value.equals(null) && value.getClass().isArray())
				{
					int length = Array.getLength(value);
					for(int index = 0; index<length; index++)
					{
						if(Arrays.asList(PRIMITIVE_WRAPPERS).contains(value.getClass().getComponentType()) || value.getClass().getComponentType().isPrimitive())
						{
							printInspectedName(Array.get(value, index), fields[i].getName() + "[" + Integer.toString(index)+ "] = ");
						}
						
						else if(recursive)
						{
							
						}
						
						else
						{
							printInspectedName(Array.get(value, index).hashCode(), fields[i].getName() + "[" + Integer.toString(index)+ "] address (HashCode) ");
						}
					}
				}
				
				else if (Arrays.asList(PRIMITIVE_WRAPPERS).contains(value.getClass()) || value.getClass().isPrimitive())
				{
					printInspectedName(value.toString(), fields[i].getName() + " = ");
				}
				
				else if(recursive)
				{
					
				}
				
				else
				{
					printInspectedName(value.hashCode(), fields[i].getName() + " address(HashCode) is ");
				}
			} 
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void printFieldsValuesRecursively(Field field, Object obj)
	{
		
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
