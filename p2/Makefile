AlgorithmEngineerTests.class: AlgorithmEngineerTests.java PokemonAE.java RbtAE.java
	javac -cp junit-platform-console-standalone-1.9.2.jar:. AlgorithmEngineerTests.java

RbtAE.class: RbtAE.java PokemonAE.java
	javac RbtAE.java

PokemonAE.class: PokemonAE.java
	javac PokemonAE.java

runTests: AlgorithmEngineerTests.class
	java -jar junit-platform-console-standalone-1.9.2.jar -cp . -c AlgorithmEngineerTests

clean:
	rm -f *.class
	rm -f *~