package IC.lir;

import java.util.ArrayList;
import java.util.List;


public class ClassLayout {
	public static final String MAIN_METHOD_LABEL = "__ic_main";
	private String className;
	private List<MethodStrc> allMethods;
	private List<MethodStrc> virtualMethods;
	private List<String> fields;
	//DVPtr = 0;
	
	public ClassLayout(String className, ClassLayout superClassLayout) {
		this.className = "_DV_" + className;
		this.allMethods = new ArrayList<MethodStrc>();
		this.virtualMethods = new ArrayList<MethodStrc>();
		this.fields = new ArrayList<String>();
		if (superClassLayout != null) {
			this.allMethods.addAll(superClassLayout.allMethods);
			this.virtualMethods.addAll(superClassLayout.virtualMethods);
			this.fields.addAll(superClassLayout.fields);
		}
	}

	
	public String getClassName() {
		return className;
	}

	/**
	 * 
	 * @param m Static method to add to method offset
	 * @return the method's offset
	 */
	public void addStaticMethod(String methodName) {
		MethodStrc methodStrc;
		if (!methodName.equals("main")) {
			int existingMethodStrcIndex = findMethodIndex(allMethods, methodName);
			if (existingMethodStrcIndex != -1)  {// overriding case:
				methodStrc = new MethodStrc(methodName, this.className);
				allMethods.remove(existingMethodStrcIndex);
				allMethods.add(existingMethodStrcIndex, methodStrc);
				return;
			}
		}
		methodStrc = new MethodStrc(methodName, this.className);
		allMethods.add(methodStrc);
		
	}

	/**
	 * 
	 * @param m Virtual method to add to method offset
	 * @return the method's offset
	 */
	public void addVirtualMethod(String methodName) {
		MethodStrc methodStrc;
		int existingAllMethodStrcIndex = findMethodIndex(allMethods, methodName);
		if (existingAllMethodStrcIndex != -1)  {// overriding case:
			int existingVirtualMethodStrcIndex = findMethodIndex(virtualMethods, methodName);
			methodStrc = new MethodStrc(methodName, this.className);
			allMethods.remove(existingAllMethodStrcIndex);
			virtualMethods.remove(existingVirtualMethodStrcIndex);
			allMethods.add(existingAllMethodStrcIndex, methodStrc);
			virtualMethods.add(existingVirtualMethodStrcIndex, methodStrc);
			return;
		}
		methodStrc = new MethodStrc(methodName, this.className);
		allMethods.add(methodStrc);
		virtualMethods.add(methodStrc);
	}
	
	/**
	 * 
	 * @param f Field to add to field offset
	 * @return the field's offset
	 */
	public void addField(String fieldName) {
		fields.add(fieldName);
	}
	
	/**
	 * 
	 * @return size in bytes needed to allocate this class
	 */
	public int getAllocatedSize() {
		return (fields.size() + 1) * 4; //4 bytes (32 bits) per field, + 4 bytes for DVPtr
	}
	
	public int getFieldIndex(String fieldName) {
		return fields.indexOf(fieldName) + 1;
	}
	
	public int getMethodIndex(String methodName) {
		int i = 0;
		for (MethodStrc methodStrc : virtualMethods) {
			if (methodStrc.methodName.equals(methodName))
				return i;
			i++;
		}
		
		return -1;
	}
	
	public String getMethodString(String methodName) {
		for (MethodStrc methodStrc : allMethods) {
			if (methodStrc.methodName.equals(methodName))
				return methodStrc.toString();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(className +": [");
		
		//sort methods according to their offsets
		for (int i = 0; i < virtualMethods.size(); i++) {
			sb.append(virtualMethods.get(i).toString());
			if(i < virtualMethods.size() - 1)
				sb.append(",");
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	public String toAssemblyLineString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(className +":\t.long ");
		for (int i = 0; i < virtualMethods.size(); i++) {
			sb.append(virtualMethods.get(i).toString());
			if(i < virtualMethods.size() - 1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	private int findMethodIndex(List<MethodStrc> list, String methodName) {
		int i = 0;
		for (MethodStrc methodStrc : list) {
			if (methodStrc.methodName.equals(methodName))
				return i;
			i++;
		}
		
		return -1;
	}
	
	private int findMethodIndex(List<MethodStrc> list, String methodName, String className) {
		int i = 0;
		for (MethodStrc methodStrc : list) {
			if (methodStrc.methodName.equals(methodName) && methodStrc.clsName.equals(className))
				return i;
			i++;
		}
		
		return -1;
	}
	
	private class MethodStrc {
		private String methodName;
		private String clsName;
		
		public MethodStrc(String methodName, String clsName) {
			this.methodName = methodName;
			this.clsName = clsName;
		}
		
		@Override
		public String toString() {
			if (methodName.equals("main"))
				return  MAIN_METHOD_LABEL;
			return /*"_" +*/ clsName + "_" + methodName;
		}
	}
}
