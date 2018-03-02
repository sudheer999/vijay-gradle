package com.dikshatech.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

// import com.dikshatech.agent.config.AgentProperties;
public abstract class PropertyLoader {

	/**
	 * Looks up a resource named 'name' in the classpath. The resource must map
	 * to a file with .properties extention. The name is assumed to be absolute
	 * and can use either "/" or "." for package segment separation with an
	 * optional leading "/" and optional ".properties" suffix. Thus, the
	 * following names refer to the same resource:
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Resource
	 * some/pkg/Resource.properties
	 * /some/pkg/Resource
	 * /some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 *            classpath resource name [may not be null]
	 * @param loader
	 *            classloader through which to load the resource [null is
	 *            equivalent to the application loader]
	 * @return resource converted to java.util.Properties [may be null if the
	 *         resource was not found and THROW_ON_LOAD_FAILURE is false]
	 * @throws IllegalArgumentException
	 *             if the resource was not found and THROW_ON_LOAD_FAILURE is
	 *             true
	 */
	private static String	PORTAL_HOME	= null;
	private static Logger	log			= LoggerUtil.getLogger(PropertyLoader.class);

	public static Properties loadProperties(String name, ClassLoader loader) {
		if (name == null) throw new IllegalArgumentException("null input: name");
		if (name.startsWith("/")) name = name.substring(1);
		if (name.endsWith(SUFFIX)) name = name.substring(0, name.length() - SUFFIX.length());
		Properties result = null;
		try{
			name = name.replace('.', File.separator.charAt(0));
			if (!name.endsWith(SUFFIX)) name = name.concat(SUFFIX);
			String propsFilePath = PORTAL_HOME + File.separator + name;
			log.debug("propsFilePath is :" + propsFilePath);
			Properties props = new Properties();
			props.load(new FileInputStream(new File(propsFilePath)));
			if (props.size() > 0) result = props;
		} catch (Exception e){
			// e.printStackTrace();
		}
		/*
		 * InputStream in = null;
		 * try
		 * {
		 * if (loader == null)
		 * loader = ClassLoader.getSystemClassLoader();
		 * if (LOAD_AS_RESOURCE_BUNDLE)
		 * {
		 * name = name.replace('/', '.');
		 * // Throws MissingResourceException on lookup failures:
		 * final ResourceBundle rb = ResourceBundle.getBundle(name, Locale.getDefault(), loader);
		 * result = new Properties();
		 * for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();)
		 * {
		 * final String key = (String) keys.nextElement();
		 * final String value = rb.getString(key);
		 * result.put(key, value);
		 * }
		 * }
		 * else
		 * {
		 * name = name.replace('.', '/');
		 * if (!name.endsWith(SUFFIX))
		 * name = name.concat(SUFFIX);
		 * // Returns null on lookup failures:
		 * in = loader.getResourceAsStream(name);
		 * if (in != null)
		 * {
		 * result = new Properties();
		 * result.load(in); // Can throw IOException
		 * }
		 * }
		 * } catch (Exception e)
		 * {
		 * e.printStackTrace(System.out);
		 * result = null;
		 * } finally
		 * {
		 * if (in != null)
		 * try
		 * {
		 * in.close();
		 * } catch (Throwable ignore)
		 * {
		 * }
		 * }
		 */
		if (THROW_ON_LOAD_FAILURE && (result == null)){
			// throw new IllegalArgumentException("could not load [" + name + "]" + " as "
			// + (LOAD_AS_RESOURCE_BUNDLE ? "a resource bundle" : "a classloader resource"));
			throw new IllegalArgumentException("could not load [" + name + "]" + " in " + (LOAD_AS_RESOURCE_BUNDLE ? "a resource bundle" : "PORTAL_HOME : " + PORTAL_HOME));
		}
		return result;
	}

	/**
	 * A convenience overload of {@link #loadProperties(String, ClassLoader)} that uses the current
	 * thread's context classloader.
	 */
	public static Properties loadProperties(final String name) {
		if (PORTAL_HOME == null){
			log.debug("PORTAL_HOME is null");
			PORTAL_HOME = System.getenv("PORTAL_HOME");
			// gurunath added for local.
			if (PORTAL_HOME == null) PORTAL_HOME = getPortalPath();
			log.debug("PORTAL_HOME is :" + PORTAL_HOME);
		}
		return loadProperties(name, null);
	}

	public static String getEnvVariable() {
		if (PORTAL_HOME == null){
			log.debug("PORTAL_HOME is null");
			PORTAL_HOME = System.getenv("PORTAL_HOME");
			// gurunath added for local.
			if (PORTAL_HOME == null) PORTAL_HOME = getPortalPath();
			log.debug("PORTAL_HOME is :" + PORTAL_HOME);
		}
		return PORTAL_HOME;
	}

	private static boolean		THROW_ON_LOAD_FAILURE	= true;
	private static boolean		LOAD_AS_RESOURCE_BUNDLE	= false;
	private static final String	SUFFIX					= ".properties";

	public static String getPortalPath() {
		if (System.getProperty("os.name").startsWith("Windows") && new File("C:/ApacheTomcat6/lib/portal").exists()) return "C:/ApacheTomcat6/lib/portal";
		else if (new File("/opt/dev-env/praya-java/ApacheTomcat6/lib/portal").exists()) return "/opt/dev-env/praya-java/ApacheTomcat6/lib/portal";
		return null;
	}
} // End of class
