import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class ScriptRunner {


    private static final Map<String, ScriptEngine> engineCache = new HashMap<>();


    private static final ExecutorService executor = Executors.newCachedThreadPool();


    public static Object runScript(String language, String script) throws Exception {
        if (script == null || script.trim().isEmpty()) {
            throw new IllegalArgumentException("Script cannot be null or empty");
        }


        language = language.toLowerCase();


        if (!language.equals("javascript") && !language.equals("python")) {
            throw new UnsupportedOperationException("Unsupported language: " + language);
        }


        ScriptEngine engine = getScriptEngine(language);


        return executeScript(engine, script);
    }


    public static Object runScriptFromFile(String language, String scriptFile) throws Exception {
        if (scriptFile == null || scriptFile.trim().isEmpty()) {
            throw new IllegalArgumentException("Script file path cannot be null or empty");
        }

        try (FileReader reader = new FileReader(scriptFile)) {
            ScriptEngine engine = getScriptEngine(language.toLowerCase());
            return executeScript(engine, reader);
        } catch (IOException e) {
            throw new IOException("Error reading script file: " + e.getMessage(), e);
        }
    }


    private static synchronized ScriptEngine getScriptEngine(String language) {

        ScriptEngine engine = engineCache.get(language);

        if (engine == null) {
            ScriptEngineManager manager = new ScriptEngineManager();

            switch (language) {
                case "javascript":

                    engine = manager.getEngineByName("nashorn");
                    if (engine == null) {

                        engine = manager.getEngineByName("graal.js");
                        if (engine == null) {
                            engine = manager.getEngineByMimeType("application/javascript");
                            if (engine == null) {
                                throw new UnsupportedOperationException(
                                    "No JavaScript engine found. Make sure you have a compatible JDK version " +
                                    "or add GraalVM JavaScript to your classpath."
                                );
                            }
                        }
                    }
                    break;

                case "python":

                    engine = manager.getEngineByName("python");
                    if (engine == null) {
                        throw new UnsupportedOperationException(
                            "No Python engine found. Make sure you have Jython added to your classpath."
                        );
                    }
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported language: " + language);
            }


            engineCache.put(language, engine);
        }

        return engine;
    }


    private static Object executeScript(ScriptEngine engine, String script) throws Exception {
        return executeScript(engine, new StringReader(script));
    }


    private static Object executeScript(final ScriptEngine engine, final Reader reader) throws Exception {

        Callable<Object> task = () -> {

            Bindings bindings = engine.createBindings();


            if (engine instanceof Compilable) {
                Compilable compilable = (Compilable) engine;
                CompiledScript compiledScript = compilable.compile(reader);
                return compiledScript.eval(bindings);
            } else {

                return engine.eval(reader, bindings);
            }
        };


        Future<Object> future = executor.submit(task);
        try {

            return future.get(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
            throw new ScriptException("Script execution failed or timed out: " + e.getMessage());
        }
    }


    public static void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}