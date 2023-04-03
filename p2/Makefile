# General
run: Pokeylog.class
	java Pokeylog

Pokeylog.class: Pokeylog.java
	javac Pokeylog.java

runTests: runAlgorithmEngineerTests runDataWranglerTests runFrontendDeveloperTests runBackendDeveloperTests

# AE
AlgorithmEngineerTests.class: AlgorithmEngineerTests.java PokemonAE.java RbtAE.java
	#javac -cp junit-platform-console-standalone-1.9.2.jar:. AlgorithmEngineerTests.java
	javac -cp junit5.jar:. AlgorithmEngineerTests.java

RbtAE.class: RbtAE.java PokemonAE.java
	javac RbtAE.java

PokemonAE.class: PokemonAE.java
	javac PokemonAE.java

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	#java -jar junit-platform-console-standalone-1.9.2.jar -cp . -c AlgorithmEngineerTests
	java -jar junit5.jar -cp . -c AlgorithmEngineerTests

# DW
DataWranglerTests.class: DataWranglerTests.java PokemonDW.java CatalogReaderDW.java
	javac -cp junit5.jar:. DataWranglerTests.java

CatalogReaderDW.class: CatalogReaderDW.java PokemonDW.java
	javac CatalogReaderDW.java

PokemonDW.class: PokemonDW.java
	javac PokemonDW.java

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . -c DataWranglerTests

# FD
runFrontendDeveloperTests: FrontendDeveloperTests.class
	java -jar junit5.jar -cp . -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendFD.java BackendFD.java TextUITester.java
	javac -cp junit5.jar:. FrontendDeveloperTests.java

FrontendFD.class: FrontendFD.java
	javac FrontendFD.java

BackendFD.class: BackendFD.java
	javac BackendFD.java

PokemonFD.class: PokemonFD.java
	javac PokemonFD.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

# BD
runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests
BackendDeveloperTests.class: BackendDeveloperTests.java BackendBD.class
	javac -cp .:junit5.jar BackendDeveloperTests.java
BackendBD.class: BackendBD.java  IBackend.java
	javac BackendBD.java  IBackend.java
CatalogReaderBD.class: CatalogReaderBD.java ICatalogReader.java
	javac CatalogReaderBD.java ICatalogReader.java 
PokemonBD.class: PokemonBD.java IPokemon.java
	javac PokemonBD.java IPokemon.java
RbtBD.class: RbtBD.java IRbt.java
	javac RbtBD.java IRbt.java
PokemonType.class: PokemonType.java
	javac PokemonType.java
SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java


clean:
	rm -f *.class
	rm -f *~
