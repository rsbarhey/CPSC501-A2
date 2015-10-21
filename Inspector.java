import java.lang.reflect.*;
import java.lang.Object;
import java.lang.Class;
import java.util.Arrays;
import java.util.HashSet;

public class Inspector
{
	private Class inspectedClass;
	private Class inspectedSuperClass;

	private Constructor[] inspectedConstructors;
	private Method[] inspectedMethods;
	private Field[] inspectedFields;
	private Class[] inspectedInterfaces;
	private HashSet printedObjectsValues;
	
	final Class<?>[] PRIMITIVE_WRAPPERS = {Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, String.class};

	public void inspect(Object obj, boolean recursive)
	{
		printedObjectsValues = new HashSet();
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
		System.out.println("_______________________________________________________________________________________________");
		printInspectedName(inspectedSuperClass, "Inspecting imidiate super: \t\t");
		traverseSuperClass(inspectedSuperClass, inspectedClass);
		System.out.println("_______________________________________________________________________________________________");
		System.out.println();
		
		System.out.println("_______________________________________________________________________________________________");
		printListOfInspectedNames(inspectedInterfaces, "Inspecteing imidiate interfaces: \t\t");
		traverseInterfaces(inspectedInterfaces, inspectedClass);
		System.out.println("_______________________________________________________________________________________________");
		System.out.println();
		
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
		if (superClass == null)
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
		System.out.println("Printing the values for object of "+ obj.getClass().getName() +" \t recursion is set to: " + Boolean.toString(recursive));
		
		if(obj.getClass().isArray())
		{
			printingArrayValue(obj, recursive);
		}
		
		else{
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
				
					else if(value.getClass().isArray())
					{
						printingArrayValue(value, recursive);
					}
				
					else if (Arrays.asList(PRIMITIVE_WRAPPERS).contains(value.getClass()) || value.getClass().isPrimitive())
					{
						printInspectedName(value, fields[i].getName() + " = ");
					}
				
					else if(recursive)
					{
						System.out.println();
						printFieldsValuesRecursively(fields[i].getName(), fields[i].getType(), value);
						System.out.println();
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
	}
	
	private void printFieldsValuesRecursively(String memberName, Class<?> type, Object fieldValue)
	{
		if(printedObjectsValues.contains(fieldValue))
		{
			System.out.println("Object has already been printed");
			return;
		}
		if(fieldValue == null || Arrays.asList(PRIMITIVE_WRAPPERS).contains(fieldValue.getClass()) || fieldValue.getClass().isPrimitive())
		{
			printInspectedName(fieldValue, memberName + " = ");
		}
		else if(fieldValue.getClass().isArray())
		{
			int length = Array.getLength(fieldValue);
			System.out.println();
			System.out.println(fieldValue.getClass().getName() + " "+ memberName);
			for(int index = 0; index<length; index++)
			{
				if(Arrays.asList(PRIMITIVE_WRAPPERS).contains(fieldValue.getClass().getComponentType()) || fieldValue.getClass().getComponentType().isPrimitive() || Array.get(fieldValue, index) == null)
				{
					printInspectedName(Array.get(fieldValue, index), memberName + "[" + Integer.toString(index)+ "] = ");
				}
				
				else
				{
					printFieldsValuesRecursively(memberName + "[" + Integer.toString(index)+ "]", fieldValue.getClass().getComponentType(), Array.get(fieldValue, index));
				}
			}
			System.out.println();
		}
		else
		{
			System.out.println(memberName+ " object has members: ");
			Field[] fieldMembers = type.getDeclaredFields();
			for(int i = 0; i < fieldMembers.length; i++)
			{
				try 
				{
					fieldMembers[i].setAccessible(true);
					Object value = fieldMembers[i].get(fieldValue);
					printedObjectsValues.add(fieldValue);
					printFieldsValuesRecursively(fieldMembers[i].getName(), fieldMembers[i].getType(), value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	private void printingArrayValue(Object obj, boolean recursive)
	{
		int length = Array.getLength(obj);
		System.out.println();
		System.out.println(obj.getClass().getName() + " "+ obj.getClass().getName());
		for(int index = 0; index<length; index++)
		{
			if(Arrays.asList(PRIMITIVE_WRAPPERS).contains(obj.getClass().getComponentType()) || obj.getClass().getComponentType().isPrimitive() || Array.get(obj, index) == null)
			{
				printInspectedName(Array.get(obj, index), obj.getClass().getName() + "[" + Integer.toString(index)+ "] = ");
			}
			
			else if(recursive)
			{
				printFieldsValuesRecursively(obj.getClass().getName() + "[" + Integer.toString(index)+ "]", obj.getClass().getComponentType(), Array.get(obj, index));
			}
			
			else
			{
				printInspectedName(Array.get(obj, index).hashCode(), obj.getClass().getName() + "[" + Integer.toString(index)+ "] address (HashCode) ");	
			}
		}
		System.out.println();
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
