package examples.showcase.bytecode;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JavassistTest {

	private CtClass ctClass;

	@Before
	public void init() throws NotFoundException {
		ClassPool classPool = ClassPool.getDefault();
		ctClass = classPool.get("examples.showcase.User");
	}

	@Test
	public void constructor() {
		CtConstructor[] ctConstructors = ctClass.getConstructors();
		for (CtConstructor ctConstructor : ctConstructors) {
			System.out.println(ctConstructor.getLongName());
		}
	}

	@Test
	public void field() {
		CtField[] ctFields = ctClass.getDeclaredFields();
		for (CtField ctField : ctFields) {
			System.out.println(ctField.getName());
		}
	}

	@Test
	public void method() {
		CtMethod[] ctMethods = ctClass.getDeclaredMethods();
		for (CtMethod ctMethod : ctMethods) {
			System.out.println(ctMethod.getLongName());
		}
	}

	@Test
	public void annotation() throws ClassNotFoundException {
		Object[] anns = ctClass.getAnnotations();
		System.out.println(Arrays.toString(anns));
	}

	@Test
	public void other() throws NotFoundException {
		System.out.println(ctClass.getModifiers());
		System.out.println(Arrays.toString(ctClass.getInterfaces()));
		System.out.println(ctClass.getPackageName());
		System.out.println(ctClass.getURL());

		System.out.println(ctClass.isAnnotation());
		System.out.println(ctClass.isArray());
		System.out.println(ctClass.isEnum());
		System.out.println(ctClass.isFrozen());
		System.out.println(ctClass.isInterface());
		System.out.println(ctClass.isModified());
		System.out.println(ctClass.isPrimitive());
	}
}