Java Service Provider Interface - 

	Java service provider interface is Java language implementation to locate services at run time and provide the instance of services to use. SPI in Kotlin is same as Java.

Components of this POC - 

	This POC explores the use of Java SPI to transform input payload to partner’s payload and vice versa. It has following components - 

    TransformerService - 

        This gradle module defines the contract for the services. It also defines the DSL for JSON and contains a service loader for locating and reloading services.

        To publish the jar to maven local, build the project in Idea and run ‘jar, install’ gradle tasks.


    InsurerOneTransformerServiceProvider - 

        This gradle module implements the service for insurer one and it leverages the DSL for composing JSON.

        To copy the jar to the shared location, build the project and run ‘copyJar’ gradle task.


    InsurerTwoTransformerServiceProvider - 
        This module implements the service for insurer two.

        To copy the jar to the shared location, build the project and run ‘copyJar’ gradle task.


    transformer-service-tester - 
        This is a basic UI app which uses the services.
        This UI app is created using the Quarkus framework and it is also a gradle project.

        To run the app, run ‘quarkusDev’ gradle task.

Dependency - 
	The tester depends on mountebank services defined project.