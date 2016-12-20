package examples.showcase.bytecode;

import javassist.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class JavassistTest {

    private static final Logger logger = LogManager.getLogger(JavassistTest.class);

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
            logger.info(ctConstructor.getLongName());
        }
    }

    @Test
    public void field() {
        CtField[] ctFields = ctClass.getDeclaredFields();
        for (CtField ctField : ctFields) {
            logger.info(ctField.getName());
        }
    }

    @Test
    public void method() {
        CtMethod[] ctMethods = ctClass.getDeclaredMethods();
        for (CtMethod ctMethod : ctMethods) {
            logger.info(ctMethod.getLongName());
        }
    }

    @Test
    public void annotation() throws ClassNotFoundException {
        Object[] anns = ctClass.getAnnotations();
        logger.info(Arrays.toString(anns));
    }

    @Test
    public void other() throws NotFoundException {
        logger.info(ctClass.getModifiers());
        logger.info(Arrays.toString(ctClass.getInterfaces()));
        logger.info(ctClass.getPackageName());
        logger.info(ctClass.getURL());

        logger.info(ctClass.isAnnotation());
        logger.info(ctClass.isArray());
        logger.info(ctClass.isEnum());
        logger.info(ctClass.isFrozen());
        logger.info(ctClass.isInterface());
        logger.info(ctClass.isModified());
        logger.info(ctClass.isPrimitive());
    }
}