import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;


public class ScriptingClient {
    
    private static final ScriptingService service = new ScriptingService();
    
    public static void main(String[] args) {
        try {

            if (args.length >= 2) {
                String language = args[0].toLowerCase();
                String scriptPath = args[1];
                runScriptFromFile(language, scriptPath);
                return;
            }
            

            runInteractiveDemo();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up resources
            service.shutdown();
        }
    }
    
    
    private static void runInteractiveDemo() throws Exception {
        System.out.println("=== Java-based Scripting Engine Demo ===");
        System.out.println("This demo will run various script examples.\n");
        

        runBasicJavaScriptExample();
        

        runJavaScriptObjectExample();
        

        runPythonExample();
        

        runJavaScriptWithLibrariesExample();
        

        runJavaScriptIntegrationExample();
        

        runScriptFileExample();
        

        runInteractiveScriptExample();
        
        System.out.println("\n=== Demo Completed Successfully ===");
    }
    
   
    private static void runBasicJavaScriptExample() throws Exception {
        System.out.println("\n=== Example 1: Basic JavaScript ===");
        
        String jsScript = 
            "// Simple calculation script\n" +
            "var x = 10;\n" +
            "var y = 20;\n" +
            "var result = x + y;\n" + 
            "// Return the result\n" +
            "result;";
        
        System.out.println("Running script:\n" + jsScript);
        Object result = service.runScript("javascript", jsScript);
        System.out.println("Result: " + result + " (type: " + result.getClass().getName() + ")");
    }
    
    
    private static void runJavaScriptObjectExample() throws Exception {
        System.out.println("\n=== Example 2: JavaScript with Objects ===");
        
        String jsObjectScript = 
            "// Create a person object\n" +
            "var person = {\n" +
            "  name: 'John Doe',\n" +
            "  age: 30,\n" +
            "  isActive: true,\n" +
            "  address: {\n" +
            "    street: '123 Main St',\n" +
            "    city: 'Boston',\n" +
            "    zipCode: '02110'\n" +
            "  },\n" +
            "  skills: ['Java', 'JavaScript', 'Python'],\n" +
            "  toString: function() { return this.name + ' (' + this.age + ')'; }\n" +
            "};\n" +
            "\n" +
            "// Return the person object\n" +
            "person;";
        
        System.out.println("Running script:\n" + jsObjectScript);
        Object result = service.runScript("javascript", jsObjectScript);
        System.out.println("Result: " + result);
        
        // The result is typically a ScriptObjectMirror or similar object that represents
        // the JavaScript object in Java. Depending on the engine, you might be able to
        // access properties and methods.
        System.out.println("Object type: " + result.getClass().getName());
    }
    
  
    private static void runPythonExample() {
        System.out.println("\n=== Example 3: Python Example ===");
        
        String pyScript = 
            "# Define a function to calculate factorial\n" +
            "def calculate_factorial(n):\n" +
            "    if n == 0 or n == 1:\n" +
            "        return 1\n" +
            "    else:\n" +
            "        return n * calculate_factorial(n-1)\n" +
            "\n" +
            "# Calculate factorial of 5\n" +
            "result = calculate_factorial(5)\n" +
            "\n" +
            "# Create a simple class\n" +
            "class Person:\n" +
            "    def __init__(self, name, age):\n" +
            "        self.name = name\n" +
            "        self.age = age\n" +
            "    \n" +
            "    def greet(self):\n" +
            "        return \"Hello, my name is \" + self.name\n" +
            "\n" +
            "# Create an instance\n" +
            "person = Person(\"Alice\", 25)\n" +
            "\n" +
            "# Return the factorial result\n" +
            "result";
        
        System.out.println("Running script:\n" + pyScript);
        
        try {
            Object result = service.runScript("python", pyScript);
            System.out.println("Result: " + result + " (type: " + result.getClass().getName() + ")");
        } catch (UnsupportedOperationException e) {
            System.out.println("Python execution requires Jython: " + e.getMessage());
            System.out.println("Add Jython to your classpath to enable Python script execution.");
        } catch (Exception e) {
            System.out.println("Error executing Python script: " + e.getMessage());
        }
    }
    
    /**
     * Example 4: JavaScript with Libraries
     */
    private static void runJavaScriptWithLibrariesExample() throws Exception {
        System.out.println("\n=== Example 4: JavaScript with Libraries ===");
        
        String jsLibScript = 
            "// Using JSON library for data manipulation\n" +
            "var jsonStr = '{\"name\":\"Alice\",\"age\":25,\"city\":\"New York\"}';\n" +
            "\n" +
            "// Parse JSON string to object\n" +
            "var person = JSON.parse(jsonStr);\n" +
            "\n" +
            "// Modify properties\n" +
            "person.age += 1;\n" +
            "person.skills = ['AI', 'Data Science'];\n" +
            "person.active = true;\n" +
            "\n" +
            "// Convert back to JSON string\n" +
            "var updatedJson = JSON.stringify(person, null, 2);\n" +
            "\n" +
            "// Return the formatted JSON\n" +
            "updatedJson;";
        
        System.out.println("Running script:\n" + jsLibScript);
        Object result = service.runScript("javascript", jsLibScript);
        System.out.println("Result:\n" + result);
    }
    
   
    private static void runJavaScriptIntegrationExample() throws Exception {
        System.out.println("\n=== Example 5: Java-JavaScript Integration ===");
        
        String integrationScript = 
            "// Using Java classes from JavaScript\n" +
            "// Create a Java ArrayList\n" +
            "var ArrayList = Java.type('java.util.ArrayList');\n" +
            "var HashMap = Java.type('java.util.HashMap');\n" +
            "\n" +
            "// Create a list\n" +
            "var list = new ArrayList();\n" +
            "list.add('Item 1');\n" +
            "list.add('Item 2');\n" +
            "list.add('Item 3');\n" +
            "\n" +
            "// Create a map\n" +
            "var map = new HashMap();\n" +
            "map.put('key1', 'value1');\n" +
            "map.put('key2', 'value2');\n" +
            "\n" +
            "// Create a result object\n" +
            "var result = {\n" +
            "  list: list,\n" +
            "  map: map,\n" +
            "  message: 'Created from JavaScript'\n" +
            "};\n" +
            "\n" +
            "// Return the Java ArrayList\n" +
            "list;";
        
        System.out.println("Running script:\n" + integrationScript);
        Object result = service.runScript("javascript", integrationScript);
        
        if (result instanceof List) {
            List<?> resultList = (List<?>) result;
            System.out.println("Result: Java List created in JavaScript: " + resultList);
            System.out.println("List size: " + resultList.size());
            System.out.println("List items:");
            
            for (int i = 0; i < resultList.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + resultList.get(i));
            }
        } else {
            System.out.println("Result: " + result);
        }
    }
    
   
    private static void runScriptFileExample() throws Exception {
        System.out.println("\n=== Example 6: File-based Script Execution ===");
        

        String tempDir = System.getProperty("java.io.tmpdir");
        String scriptFilePath = tempDir + File.separator + "temp_script_" + System.currentTimeMillis() + ".js";
        File scriptFile = new File(scriptFilePath);
        

        String scriptContent = 
            "// This script is loaded from a file\n" +
            "function calculateSum(numbers) {\n" +
            "  var sum = 0;\n" +
            "  for (var i = 0; i < numbers.length; i++) {\n" +
            "    sum += numbers[i];\n" +
            "  }\n" +
            "  return sum;\n" +
            "}\n" +
            "\n" +
            "// Create an array of numbers\n" +
            "var numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];\n" +
            "\n" +
            "// Calculate and return the sum\n" +
            "var result = {\n" +
            "  numbers: numbers,\n" +
            "  sum: calculateSum(numbers),\n" +
            "  average: calculateSum(numbers) / numbers.length\n" +
            "};\n" +
            "\n" +
            "// Return the result object\n" +
            "result;";
        
        try (FileWriter writer = new FileWriter(scriptFile)) {
            writer.write(scriptContent);
        }
        
        System.out.println("Created temporary script file: " + scriptFilePath);
        System.out.println("Running script from file...");
        

        Object result = service.runScriptFromFile("javascript", scriptFilePath);
        System.out.println("Result: " + result);
        

        scriptFile.delete();
        System.out.println("Temporary script file deleted.");
    }
    
   
    private static void runInteractiveScriptExample() {
        System.out.println("\n=== Example 7: Interactive Script Execution ===");
        System.out.println("Enter a simple JavaScript expression to evaluate (or 'exit' to quit):");
        
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine().trim();
        
        if (!line.equalsIgnoreCase("exit")) {
            try {
                Object result = service.runScript("javascript", line);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error executing script: " + e.getMessage());
            }
        }
    }
    
    
    private static void runScriptFromFile(String language, String scriptPath) throws Exception {
        File scriptFile = new File(scriptPath);
        
        if (!scriptFile.exists()) {
            System.err.println("Script file not found: " + scriptPath);
            return;
        }
        
        System.out.println("Running " + language + " script from: " + scriptPath);
        
        try {
            Object result = service.runScriptFromFile(language, scriptPath);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error executing script: " + e.getMessage());
            throw e;
        }
    }
}