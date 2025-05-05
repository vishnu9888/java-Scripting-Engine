Java Scripting Engine

This project allows you to run JavaScript and Python scripts directly from Java without using external tools or command-line environments. It works entirely inside the Java Virtual Machine (JVM).

Key Features:

- You can run JavaScript or Python code from strings or files.
- The output of the script can be used back in your Java program.
- It supports passing values into the script like environment variables.
- The system is thread-safe and supports timeouts.
- It handles script errors and cleans up after execution.

Requirements:

- Java 8 or newer.
- For JavaScript:
  - If using Java 8 to 14, it uses the built-in Nashorn engine.
  - From Java 15 onward, it needs GraalVM or another engine that supports JSR-223.
- For Python:
  - It uses Jython, which must be added to the classpath.

How to Use:

Example for JavaScript:

Create an instance of the engine and run a JavaScript script:

JavaScriptingEngine engine = new JavaScriptingEngine();
String script = "var x = 10; var y = 20; x + y;";
Object result = engine.runScript("javascript", script);
System.out.println("Result: " + result); // should show 30

Example for Python:

String script = "x = 15; y = 25; x + y";
Object result = engine.runScript("python", script);
System.out.println("Result: " + result); // should show 40

You can also pass environment variables to the script like this:

Map<String, Object> env = new HashMap<>();
env.put("username", "admin");
env.put("maxRetries", 3);
String script = "var config = { user: username, retries: maxRetries }; config;";
Object result = engine.runScript("javascript", script, env);

To run a script from a file:

Object result = engine.runScriptFromFile("javascript", "path/to/script.js");

How it Works:

The engine uses the JSR-223 scripting API in Java. The main class JavaScriptingEngine provides a simple way to run scripts. Internally, ScriptingService and ScriptRunner handle the execution.

Components:

- JavaScriptingEngine: This is what the user interacts with.
- ScriptingService: It manages script execution and environment values.
- ScriptRunner: It runs the actual scripts.

Testing:

A test class named JavaScriptingEngineTest is provided. It covers basic scenarios to check if the engine works properly.

To run the test, compile the files and run the test class:

javac *.java  
java JavaScriptingEngineTest
