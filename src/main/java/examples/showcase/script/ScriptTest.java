package examples.showcase.script;

import examples.showcase.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.script.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ScriptTest {

    private static final Logger logger = LogManager.getLogger(ScriptTest.class);

    private ScriptEngineManager engineManager;

    @Before
    public void init() {
        engineManager = new ScriptEngineManager();
    }

    @Test
    public void engineFactories() {
        List<ScriptEngineFactory> engineFactories = engineManager.getEngineFactories();
        logger.info("count: " + engineFactories.size());
        logger.info("");
        for (ScriptEngineFactory engineFactory : engineFactories) {
            logger.info(engineFactory.getEngineName());
            logger.info(engineFactory.getEngineVersion());
            logger.info(engineFactory.getLanguageName());
            logger.info(engineFactory.getLanguageVersion());
            logger.info(engineFactory.getExtensions());
            logger.info(engineFactory.getMimeTypes());
            logger.info(engineFactory.getNames());
            logger.info(StringUtils.EMPTY);
        }
    }

    @Test
    public void javascript() throws IOException, ScriptException, NoSuchMethodException {
        Invocable invoke = getInvocable();
        Object obj = invoke.invokeFunction("add", 1, 2);
        logger.info(obj);
    }

    @Test
    public void add() throws IOException, ScriptException, NoSuchMethodException {
        Function function = getFunction();
        int obj = function.add(1, 2);
        logger.info(obj);
    }

    @Test
    public void list() throws IOException, ScriptException {
        Function function = getFunction();
        ArrayList<String> list = function.list();
        logger.info(list);
    }

    @Test
    public void maps() throws IOException, ScriptException {
        Function function = getFunction();
        HashMap<Integer, String> list = function.maps();
        logger.info(list);
    }

    @Test
    public void users() throws IOException, ScriptException {
        Function function = getFunction();
        ArrayList<User> list = function.users();
        logger.info(list);
    }

    @Test
    public void print() throws IOException, ScriptException {
        Function function = getFunction();
        function.printUser(new User(1, "aaa", 11, "shanghai", new Date()));
    }

    private Invocable getInvocable() throws IOException, ScriptException {
        ScriptEngine engine = engineManager.getEngineByExtension("js");
        Resource resource = new ClassPathResource("js/function.js");
        Reader reader = new FileReader(resource.getFile());
        engine.eval(reader);
        Invocable invoke = (Invocable) engine;
        return invoke;
    }

    private Function getFunction() throws IOException, ScriptException {
        Invocable invocable = getInvocable();
        Function function = invocable.getInterface(Function.class);
        return function;
    }

}
