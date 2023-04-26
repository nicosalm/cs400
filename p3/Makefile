runTests: BackendDeveloperTests.class # TODO change to runBackendDeveloperTests in integration
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

clean:
	rm *.class