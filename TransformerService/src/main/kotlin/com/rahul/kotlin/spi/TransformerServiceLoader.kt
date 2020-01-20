package com.rahul.kotlin.spi

import java.io.File
import java.io.IOException
import java.util.ServiceLoader

/**
 *
 * @author in-rahul.khandelwal
 */
object TransformerServiceLoader {

    private lateinit var serviceLoader: ServiceLoader<TransformerServiceProvider>
    private lateinit var classLoader: CustomUrlClassLoader
    
    private var services: MutableMap<String, TransformerServiceProvider> = mutableMapOf()

    init {
        loadServices()
    }

    fun reloadServices() {
        serviceLoader.reload()

        try {
            classLoader.close()
        } catch (ex: IOException) {
            // Ignore
        }

        services.clear()
        
        loadServices()
    }

    /**
     * This method - 
     * 1. Creates the custom class loader.  
     * 2. Adds the JAR file URLs to class loader.
     * 3. Instantiate the service loader using the class loader.
     * 4. Finds the services and caches them in map.
     */
    private fun loadServices() {
        classLoader = CustomUrlClassLoader(arrayOf())

        val dir = File(System.getProperty("user.dir") + "/../../../../../TransformerServiceImpls/")
        val flist = dir.listFiles{file -> file.path.toLowerCase().endsWith(".jar")}
        
        try {
            for (file in flist) {
                classLoader.addURL(file.toURI().toURL())
            }
        } catch (ex: Exception) {
            println("Directory to load jars is : " + dir.absolutePath)
            println("Exception while loading JARs : " + ex.message)
        }
        
        serviceLoader = ServiceLoader.load(TransformerServiceProvider::class.java, classLoader)
        
        var itr = serviceLoader.iterator()

        while (itr.hasNext()) {
            val next = itr.next()
            services[next.serviceName()] = next
        }
    }

    fun getProvider(name: String) : TransformerServiceProvider? {
        return services[name];
    }
}
