# General
run: FlightPlanner.class
	java FlightPlanner

FlightPlanner.class: FlightPlanner.java
	javac FlightPlanner.java

runTests: runAlgorithmEngineerTests runDataWranglerTests runFrontendDeveloperTests runBackendDeveloperTests

clean:
	rm -f *.class
	rm -f *~

# FD
runFrontendDeveloperTests: FrontendDeveloperTests.class
	java -jar junit5.jar -cp . -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java FrontendFD.java BackendFD.java FlightFD.java TextUITester.java
	javac -cp junit5.jar:. FrontendDeveloperTests.java

FrontendFD.class: FrontendFD.java
	javac FrontendFD.java

BackendFD.class: BackendFD.java
	javac BackendFD.java

FlightFD.class: FlightFD.java
	javac FlightFD.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

# BD
runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar -cp . -c BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java BackendBD.java FlightBD.java CityBD.java FlightReaderBD.java
	javac -cp junit5.jar:. BackendDeveloperTests.java

BackendBD.class: BackendBD.java FlightBD.java CityBD.java
	javac BackendBD.java

FlightReaderBD.class: FlightReaderBD.java FlightBD.java CityBD.java
	javac FlightReaderBD.java

FlightBD.class: FlightBD.java CityBD.java
	javac FlightBD.java

CityBD.class: CityBD.java FlightBD.java
	javac CityBD.java

# DW
runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . -c DataWranglerTests
DataWranglerTests.class: DataWranglerTests.java FlightReaderDW.java CityDW.java FlightDW.java
	javac -cp .:junit5.jar DataWranglerTests.java
FlightReaderDW.class: FlightReaderDW.java CityDW.java FlightDW.java
	javac FlightReaderDW.java
City.class: CityDW.java FlightDW.java
	javac CityDW.java
Flight.class: FlightDW.java
	javac FlightDW.java

# AE
runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests
AlgorithmEngineerTests.class: AlgorithmEngineerTests.java FlightGraphAE.class FlightAE.class CityAE.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

FlightGraphAE.class: FlightGraphAE.java IFlightGraph.java FlightAE.java
	javac FlightGraphAE.java

FlightAE.class: FlightAE.java IFlight.java CityAE.java
	javac FlightAE.java

CityAE.class: CityAE.java ICity.java GraphADT.java
	javac CityAE.java
