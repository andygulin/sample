package examples.showcase.script;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import examples.showcase.User;

public class ScriptTest {

	private ScriptEngineManager engineManager;

	@Before
	public void init() {
		engineManager = new ScriptEngineManager();
	}

	@Test
	public void engineFactories() {
		List<ScriptEngineFactory> engineFactories = engineManager.getEngineFactories();
		System.out.println("count: " + engineFactories.size());
		System.out.println();
		for (ScriptEngineFactory engineFactory : engineFactories) {
			System.out.println(engineFactory.getEngineName());
			System.out.println(engineFactory.getEngineVersion());
			System.out.println(engineFactory.getLanguageName());
			System.out.println(engineFactory.getLanguageVersion());
			System.out.println(engineFactory.getExtensions());
			System.out.println(engineFactory.getMimeTypes());
			System.out.println(engineFactory.getNames());
			System.out.println();
		}
	}

	@Test
	public void javascript() throws IOException, ScriptException, NoSuchMethodException {
		Invocable invoke = getInvocable();
		Object obj = invoke.invokeFunction("add", 1, 2);
		System.out.println(obj);
	}

	@Test
	public void add() throws IOException, ScriptException, NoSuchMethodException {
		Function function = getFunction();
		int obj = function.add(1, 2);
		System.out.println(obj);
	}

	@Test
	public void list() throws FileNotFoundException, IOException, ScriptException {
		Function function = getFunction();
		ArrayList<String> list = function.list();
		System.out.println(list);
	}

	@Test
	public void maps() throws FileNotFoundException, IOException, ScriptException {
		Function function = getFunction();
		HashMap<Integer, String> list = function.maps();
		System.out.println(list);
	}

	@Test
	public void users() throws FileNotFoundException, IOException, ScriptException {
		Function function = getFunction();
		ArrayList<User> list = function.users();
		System.out.println(list);
	}

	@Test
	public void print() throws FileNotFoundException, IOException, ScriptException {
		Function function = getFunction();
		function.printUser(new User(1, "aaa", 11, "shanghai", new Date()));
	}

	private Invocable getInvocable() throws FileNotFoundException, IOException, ScriptException {
		ScriptEngine engine = engineManager.getEngineByExtension("js");
		Resource resource = new ClassPathResource("js/function.js");
		Reader reader = new FileReader(resource.getFile());
		engine.eval(reader);
		Invocable invoke = (Invocable) engine;
		return invoke;
	}

	private Function getFunction() throws FileNotFoundException, IOException, ScriptException {
		Invocable invocable = getInvocable();
		Function function = invocable.getInterface(Function.class);
		return function;
	}

}
