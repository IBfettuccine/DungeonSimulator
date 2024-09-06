import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTRequest {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("API_KEY"); //add your own api key or get it from the system environment variable
    private static String MODEL = "gpt-4o-mini";
    private static final int MAX_RETRIES = 3;
    private static final int INITIAL_RETRY_DELAY_MS = 1000;
    private static int count = 0;

    public static void main(String[] args) {
        //String question = "How would you describe this without saying the exact numbers: the adventurer does 2 damage to the goblin with his sword. The orc has a maximum of 13hp and now is at 3hp. ";
        String question = "How would you describe this in less than 50 words";
        try {
            String response = askChatGPT(question);
            System.out.println("ChatGPT Response: " + response);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static String askChatGPT(String question) throws Exception {
        int retries = 0;
        int retryDelay = INITIAL_RETRY_DELAY_MS;

        while (retries < MAX_RETRIES) {
            try {
                return makeApiCall("How would you describe this in less than 50 words without using any numbers: " + question);
            } catch (ApiException e) {
                if (e.getStatusCode() == 429) {
                    System.out.println("Rate limit hit. Retrying in " + retryDelay + "ms");
                    Thread.sleep(retryDelay);
                    retries++;
                    retryDelay *= 2; // Exponential backoff
                } else {
                    throw e; // Rethrow if it's not a rate limit error
                }
            }
        }
        throw new Exception("Max retries reached. Unable to get a response from the API.");
    }

    private static String makeApiCall(String question) throws Exception {
        URL url = new URL(API_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        switch (count) {
            case 0:
                MODEL = "gpt-4o-mini";
                count++;
                break;
            case 1:
                MODEL = "gpt-3.5-turbo";
                count++;
                break;
            case 2:
                MODEL = "gpt-3.5-turbo-0125";
                count++;
                break;
            case 3:
                MODEL = "gpt-3.5-turbo-1106";
                count++;
                break;
            case 4:
                MODEL = "gpt-3.5-turbo-16k";
                count++;
                break;
            case 5:
                MODEL = "gpt-4";
                count++;
                break;
            case 6:
                MODEL = "gpt-4-0613";
                count = 0;
                break;
        }
      //  System.out.println(MODEL);

        String jsonInputString = "{"
                + "\"model\": \"" + MODEL + "\","
                + "\"messages\": ["
                + "  {\"role\": \"system\", \"content\": \"You are a creative story writer. Do not use the words hit points. Only use words no numbers. If a character's maximum HP is the same as their current HP then no damage has been done to that character. \"},"
                + "  {\"role\": \"user\", \"content\": \"" + question.replace("\"", "\\\"") + "\"}"
                + "],"
                + "\"max_tokens\": 200"
                + "}";

       // System.out.println("Sending request: " + jsonInputString);

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(jsonInputString);
        }

        int responseCode = connection.getResponseCode();
     //   System.out.println("Response code: " + responseCode);
       // System.out.println("Response message: " + connection.getResponseMessage());

        if (responseCode == 429) {
            String retryAfter = connection.getHeaderField("Retry-After");
            System.out.println("Rate limit exceeded. Retry-After: " + retryAfter);
            throw new ApiException("Rate limit exceeded", 429);
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            throw new ApiException("HTTP error code: " + responseCode + ". Error message: " + errorResponse.toString(), responseCode);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return parseResponse(response.toString());
    }

    private static String parseResponse(String response) {
        JSONKeyValueExtractor.getContent(response);
        try {
            return JSONKeyValueExtractor.getValueForKey(response, "content");
        } catch (Exception e) {
            System.out.println("Error parsing response: " + e.getMessage());
            return "Parse error: " + e.getMessage();
        }
    }

    private static class ApiException extends Exception {
        private int statusCode;

        public ApiException(String message, int statusCode) {
            super(message);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}