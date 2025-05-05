import java.util.Map;

public class JavaScriptingEngine {

    private static final ScriptingService scriptingService = new ScriptingService();
    public Object runScript(String language, String script) throws Exception {
        return scriptingService.runScript(language, script);
    }


    public Object runScript(String language, String script, Map<String, Object> env) throws Exception {

        if (env != null) {
            for (Map.Entry<String, Object> entry : env.entrySet()) {
                scriptingService.setGlobalVariable(entry.getKey(), entry.getValue());
            }
        }

        try {

            return scriptingService.runScript(language, script);
        } finally {

            if (env != null) {
                for (String key : env.keySet()) {
                    scriptingService.removeGlobalVariable(key);
                }
            }
        }
    }


    public Object runScriptFromFile(String language, String scriptPath) throws Exception {
        return scriptingService.runScriptFromFile(language, scriptPath);
    }


    public Object runScriptFromFile(String language, String scriptPath, Map<String, Object> env) throws Exception {

        if (env != null) {
            for (Map.Entry<String, Object> entry : env.entrySet()) {
                scriptingService.setGlobalVariable(entry.getKey(), entry.getValue());
            }
        }

        try {

            return scriptingService.runScriptFromFile(language, scriptPath);
        } finally {

            if (env != null) {
                for (String key : env.keySet()) {
                    scriptingService.removeGlobalVariable(key);
                }
            }
        }
    }


    public void shutdown() {
        scriptingService.shutdown();
    }
}
