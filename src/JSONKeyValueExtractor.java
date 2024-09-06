public class JSONKeyValueExtractor {

    public static String getValueForKey(String json, String key) {
        String keyWithQuotes = "\"" + key + "\"";
        int keyIndex = json.indexOf(keyWithQuotes);

        if (keyIndex == -1) {
            return null; // Key not found
        }

        // Find the start of the value
        int colonIndex = json.indexOf(":", keyIndex);
        if (colonIndex == -1) {
            return null; // Malformed JSON
        }

        // Determine the start of the value
        int valueStartIndex = colonIndex + 1;
        char valueStartChar = json.charAt(valueStartIndex);
        while (Character.isWhitespace(valueStartChar)) {
            valueStartChar = json.charAt(++valueStartIndex);
        }

        // Determine the end of the value based on its type (string, number, boolean)
        int valueEndIndex;
        if (valueStartChar == '\"') { // String value
            valueEndIndex = json.indexOf("\"", valueStartIndex + 1);
            return json.substring(valueStartIndex + 1, valueEndIndex);
        } else { // Number or boolean value
            valueEndIndex = valueStartIndex;
            while (valueEndIndex < json.length() && !Character.isWhitespace(json.charAt(valueEndIndex)) && json.charAt(valueEndIndex) != ',' && json.charAt(valueEndIndex) != '}') {
                valueEndIndex++;
            }
            return json.substring(valueStartIndex, valueEndIndex);
        }
    }

    public static void getContent(String jsonString) {
        String name = getValueForKey(jsonString, "content");

        System.out.println(name);       // Output: John
    }
}