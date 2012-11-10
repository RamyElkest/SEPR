package model.io;


/**
 * Used to make JSON written to files easier to read (pretty!) by formatting
 * them by adding whitespace 
 * @author Llamas
 */
public class JsonPrettyFormatter {
	/**
	 * Constructor
	 */
	public JsonPrettyFormatter() {
		super();
	}
	
	
	
	/**
	 * Format JSON such that it can be pretty printed 
	 * @param	json	Json to format
	 */
	public String format(String json) {
		String prettyString = "";
				
		boolean inQuotes = false;
		boolean escaping = false;
		int nesting = 0;
		char chr;
		
		for(int i = 0; i < json.length(); i++) {
			chr = json.charAt(i); 
			
			if(chr == '\\') {
				// Escape character
				escaping = !escaping;
			}
			else {
				escaping = false;
			}
			
			if(!escaping && chr == '"') {
				// Keep track of whether quote is open
				inQuotes = !inQuotes;
			}
			
			boolean handled = false;
			if(!inQuotes) {
				handled = true;
				if(chr == ':') {
					// Open up colons
					prettyString += ": ";
				}
				else if(chr == '{') {
					// Line break objects
					nesting++;
					prettyString += "{\n"+ this.duplicateCharacter('\t', nesting);
				}
				else if(chr == '}') {
					// Line break objects
					nesting--;
					prettyString += "\n"+ this.duplicateCharacter('\t', nesting) +"}";
				}
				else if(chr == ',') {
					// Line break properties
					prettyString += ",\n"+ this.duplicateCharacter('\t', nesting);
				}
				else {
					handled = false;
				}
			}

			if(!handled) {
				// Other character
				prettyString += chr;
			}
		}
		
		return prettyString;
	}
	
	
	
	// Private methods
	/*
	 * Duplicate character a specified number of times to create a string
	 * @param	chr	Character to duplicate
	 * @param	times	Number of times to duplicate character
	 * @return	Character duplicated a specified number of times
	 */
	private String duplicateCharacter(char chr, int times) {
		String str = "";
		for(int i = 0; i < times; i++) {
			str += chr;
		}
		return str;
	}
}
