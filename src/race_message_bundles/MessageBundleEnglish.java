package race_message_bundles;

public class MessageBundleEnglish extends MessagebundleBase {
	
	static String englishBundleName = "Englishi18mText.txt";

	public MessageBundleEnglish() {
		//read strings in a text file and get variable = string
		//for each string, it will put the string in the hashmap 
		programGreeting = "Welcome to car race simulator";
		incorrectValue = "Incorrect value";
		invalidRange = "Invalid range";
		invalidName = "Invalid Name";
		
		i18nMessages.put(programGreeting, "Welcome to car race simulator");
	}
}
