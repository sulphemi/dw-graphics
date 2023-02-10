run: Main.class
	java Main

Main.class: Main.java Screen.class
	javac Main.java

Screen.class: Screen.java
	javac Screen.java

clean:
	rm *.class
