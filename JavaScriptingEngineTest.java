import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;


public class JavaScriptingEngineTest {

    private static final JavaScriptingEngine engine = new JavaScriptingEngine();

    public static void main(String[] args) {
        System.out.println("=== Java Scripting Engine Test ===");


        testJavaScript();


        testPython();

        try {

            testEnvironmentVariables();


            testFileExecution();
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        } finally {
            engine.shutdown();
        }

        System.out.println("\n=== Test Execution Completed ===");
        System.out.println("Note: Some tests may be skipped if required script engines are not available.");
        System.out.println("To enable JavaScript execution in Java 15+, add GraalVM JavaScript to your classpath.");
        System.out.println("To enable Python execution, add Jython to your classpath.");
    }


    private static void testJavaScript() {
        System.out.println("\n=== Testing JavaScript Execution ===");

        try {
            // Test 1: Simple calculation
            String jsScript1 =
                "// Simple calculation\n" +
                "var x = 10;\n" +
                "var y = 20;\n" +
                "x + y;";  // Return value

            Object result1 = engine.runScript("javascript", jsScript1);
            System.out.println("Test 1 - Simple calculation: " + result1 + " (Expected: 30)");

            // Test 2: Object creation
            String jsScript2 =
                "// Create a person object\n" +
                "var person = {\n" +
                "  name: 'John Doe',\n" +
                "  age: 30,\n" +
                "  greet: function() { return 'Hello, my name is ' + this.name; }\n" +
                "};\n" +
                "person;";  // Return the object

            Object result2 = engine.runScript("javascript", jsScript2);
            System.out.println("Test 2 - Object creation: " + result2);

            // Test 3: Function definition and execution
            String jsScript3 =
                "// Define and execute a function\n" +
                "function factorial(n) {\n" +
                "  if (n <= 1) return 1;\n" +
                "  return n * factorial(n - 1);\n" +
                "}\n" +
                "factorial(5);";  // Calculate 5!

            Object result3 = engine.runScript("javascript", jsScript3);
            System.out.println("Test 3 - Function execution: " + result3 + " (Expected: 120)");
        } catch (UnsupportedOperationException e) {
            System.out.println("JavaScript execution requires a JavaScript engine: " + e.getMessage());
            System.out.println("For Java 8-14: Nashorn is included by default");
            System.out.println("For Java 15+: Add GraalVM JavaScript to your classpath");
        } catch (Exception e) {
            System.out.println("Error executing JavaScript script: " + e.getMessage());
        }
    }

    /**
     * Test Python execution (requires Jython in the classpath)
     */
    private static void testPython() {
        System.out.println("\n=== Testing Python Execution ===");

        try {
            // Test 1: Simple calculation
            String pyScript1 =
                "# Simple calculation\n" +
                "x = 15\n" +
                "y = 25\n" +
                "x + y";  // Return value

            Object result1 = engine.runScript("python", pyScript1);
            System.out.println("Test 1 - Simple calculation: " + result1 + " (Expected: 40)");

            // Test 2: List manipulation
            String pyScript2 =
                "# Create and manipulate a list\n" +
                "numbers = [1, 2, 3, 4, 5]\n" +
                "sum_of_numbers = sum(numbers)\n" +
                "average = sum_of_numbers / len(numbers)\n" +
                "{'numbers': numbers, 'sum': sum_of_numbers, 'average': average}";

            Object result2 = engine.runScript("python", pyScript2);
            System.out.println("Test 2 - List manipulation: " + result2);

            // Test 3: Function definition and execution
            String pyScript3 =
                "# Define and execute a function\n" +
                "def factorial(n):\n" +
                "    if n <= 1:\n" +
                "        return 1\n" +
                "    return n * factorial(n - 1)\n" +
                "\n" +
                "factorial(5)";  // Calculate 5!

            Object result3 = engine.runScript("python", pyScript3);
            System.out.println("Test 3 - Function execution: " + result3 + " (Expected: 120)");

        } catch (UnsupportedOperationException e) {
            System.out.println("Python execution requires Jython: " + e.getMessage());
            System.out.println("Add Jython to your classpath to enable Python script execution.");
        } catch (Exception e) {
            System.out.println("Error executing Python script: " + e.getMessage());
        }
    }

    /**
     * Test environment variables
     */
    private static void testEnvironmentVariables() throws Exception {
        System.out.println("\n=== Testing Environment Variables ===");

        // Create environment variables
        Map<String, Object> env = new HashMap<>();
        env.put("username", "admin");
        env.put("maxRetries", 3);
        env.put("debugMode", true);
        Map<String, Object> serverConfig = new HashMap<>();
        serverConfig.put("host", "localhost");
        serverConfig.put("port", 8080);
        env.put("serverConfig", serverConfig);

        // JavaScript test with environment variables
        try {
            String jsEnvScript =
                "// Access environment variables\n" +
                "var config = {\n" +
                "  user: username,\n" +  // Access the 'username' from environment
                "  retries: maxRetries,\n" +  // Access the 'maxRetries' from environment
                "  debug: debugMode,\n" +  // Access the 'debugMode' from environment
                "  server: serverConfig\n" +  // Access the 'serverConfig' object from environment
                "};\n" +
                "config;";  // Return the config object

            Object jsResult = engine.runScript("javascript", jsEnvScript, env);
            System.out.println("JavaScript with environment variables: " + jsResult);
        } catch (UnsupportedOperationException e) {
            System.out.println("JavaScript execution requires a JavaScript engine: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing JavaScript with environment variables: " + e.getMessage());
        }

        // Python test with environment variables (if available)
        try {
            String pyEnvScript =
                "# Access environment variables\n" +
                "config = {\n" +
                "  'user': username,\n" +  // Access the 'username' from environment
                "  'retries': maxRetries,\n" +  // Access the 'maxRetries' from environment
                "  'debug': debugMode,\n" +  // Access the 'debugMode' from environment
                "  'server': serverConfig\n" +  // Access the 'serverConfig' object from environment
                "}\n" +
                "config";  // Return the config object

            Object pyResult = engine.runScript("python", pyEnvScript, env);
            System.out.println("Python with environment variables: " + pyResult);
        } catch (UnsupportedOperationException e) {
            System.out.println("Python execution requires Jython: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing Python script with environment variables: " + e.getMessage());
        }
    }

    /**
     * Test file-based script execution
     */
    private static void testFileExecution() throws Exception {
        System.out.println("\n=== Testing File-based Execution ===");

        // Create a temporary JavaScript file
        String tempDir = System.getProperty("java.io.tmpdir");
        String jsFilePath = tempDir + File.separator + "test_script_" + System.currentTimeMillis() + ".js";
        File jsFile = new File(jsFilePath);

        // JavaScript content
        String jsContent =
            "// File-based JavaScript test\n" +
            "function processData(data) {\n" +
            "  var result = {};\n" +
            "  result.original = data;\n" +
            "  result.length = data.length;\n" +
            "  result.uppercase = data.toUpperCase();\n" +
            "  result.reversed = data.split('').reverse().join('');\n" +
            "  return result;\n" +
            "}\n" +
            "\n" +
            "// Process a sample string\n" +
            "processData('Hello from JavaScript file!');";

        // Write JavaScript content to file
        try (FileWriter writer = new FileWriter(jsFile)) {
            writer.write(jsContent);
        }

        System.out.println("Created JavaScript file: " + jsFilePath);

        // Execute JavaScript from file
        try {
            Object jsResult = engine.runScriptFromFile("javascript", jsFilePath);
            System.out.println("JavaScript file execution result: " + jsResult);
        } catch (UnsupportedOperationException e) {
            System.out.println("JavaScript execution requires a JavaScript engine: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing JavaScript file: " + e.getMessage());
        } finally {
            // Clean up JavaScript file
            jsFile.delete();
            System.out.println("Deleted JavaScript file");
        }

        // Create a temporary Python file (if Python is supported)
        try {
            String pyFilePath = tempDir + File.separator + "test_script_" + System.currentTimeMillis() + ".py";
            File pyFile = new File(pyFilePath);

            // Python content
            String pyContent =
                "# File-based Python test\n" +
                "def process_data(data):\n" +
                "    result = {}\n" +
                "    result['original'] = data\n" +
                "    result['length'] = len(data)\n" +
                "    result['uppercase'] = data.upper()\n" +
                "    result['reversed'] = data[::-1]\n" +
                "    return result\n" +
                "\n" +
                "# Process a sample string\n" +
                "process_data('Hello from Python file!')";

            // Write Python content to file
            try (FileWriter writer = new FileWriter(pyFile)) {
                writer.write(pyContent);
            }

            System.out.println("Created Python file: " + pyFilePath);

            // Execute Python from file
            try {
                Object pyResult = engine.runScriptFromFile("python", pyFilePath);
                System.out.println("Python file execution result: " + pyResult);
            } catch (UnsupportedOperationException e) {
                System.out.println("Python execution requires Jython: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error executing Python file: " + e.getMessage());
            } finally {
                // Clean up Python file
                pyFile.delete();
                System.out.println("Deleted Python file");
            }
        } catch (Exception e) {
            System.out.println("Error creating Python test file: " + e.getMessage());
        }
    }
}
