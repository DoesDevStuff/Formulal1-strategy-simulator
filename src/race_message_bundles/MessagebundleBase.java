package race_message_bundles;

import java.util.HashMap;
import java.util.Map;

public class MessagebundleBase {
	public static MessagebundleBase MessagebundleToUse = new MessageBundleEnglish(); //default
	
/*
 * Working of Message bundles:
 * collect *everything* that a user will see from this program single place.
 * (sentences on Html pages, contents in debug and log files etc.)
 * 
 * There will be one "bundle" for every supported language in form of a class, which will hold
 * every sentence (message) translated in a specific language.
 * In this example, there is a base class that has empty strings, and multiple classes that extend
 * this base class that have the messages for a specific supported language
 * 
 * At the start of the program, the static variable MessagebundleToUse will be initialized by the program
 * with the instance of a class (e.g. MessageBundleEnglish or race_message_bundles) that this program is
 * going to support. All outputs will use MessagebundleToUse.xxx variable
 * 
 */
	static String basePathFori18nStrings = "../i80n";

	public String programGreeting = null;
	public String incorrectValue = null;
	public String invalidRange = null;
	public String invalidName = null;
	
	static Map i18nMessages = new HashMap();
	
}
