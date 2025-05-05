import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ScriptingService {

    private final Map<String, Object> globalContext = new ConcurrentHashMap<>();


    public Object runScript(String language, String script) throws Exception {
        try {
            return ScriptRunner.runScript(language, script);
        } catch (Exception e) {

            throw new Exception("Failed to execute " + language + " script: " + e.getMessage(), e);
        }
    }


    public Object runScriptFromFile(String language, String scriptFilePath) throws Exception {
        try {
            return ScriptRunner.runScriptFromFile(language, scriptFilePath);
        } catch (Exception e) {
            throw new Exception("Failed to execute " + language + " script from file: " + e.getMessage(), e);
        }
    }


    public void setGlobalVariable(String key, Object value) {
        globalContext.put(key, value);
    }


    public Object getGlobalVariable(String key) {
        return globalContext.get(key);
    }


    public Object removeGlobalVariable(String key) {
        return globalContext.remove(key);
    }


    public void clearGlobalContext() {
        globalContext.clear();
    }


    public void shutdown() {
        clearGlobalContext();
        ScriptRunner.shutdown();
    }
}