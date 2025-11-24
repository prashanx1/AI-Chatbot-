import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;

import java.util.*;

public class Chatbot {

    private StanfordCoreNLP pipeline;
    private String lastIntent = "";

    public Chatbot() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        this.pipeline = new StanfordCoreNLP(props);
    }

    // NLP Lemmatizer
    private List<String> getLemmas(String text) {
        CoreDocument doc = new CoreDocument(text);
        pipeline.annotate(doc);

        List<String> lemmas = new ArrayList<>();
        for (CoreLabel tok : doc.tokens()) {
            lemmas.add(tok.lemma().toLowerCase());
        }
        return lemmas;
    }

    // Intent Classification
    private String classifyIntent(List<String> lemmas) {
        if (lemmas.contains("hello") || lemmas.contains("hi") || lemmas.contains("hey"))
            return "greeting";

        if (lemmas.contains("help") || lemmas.contains("assist"))
            return "help";

        if (lemmas.contains("weather") || lemmas.contains("temperature"))
            return "weather";

        if (lemmas.contains("bye") || lemmas.contains("exit"))
            return "exit";

        return "unknown";
    }

    // Generate Response
    private String generateResponse(String intent) {

        lastIntent = intent;  // store context

        switch (intent) {
            case "greeting":
                return "Hello! How can I help you today?";
            case "help":
                return "Sure! I can answer general queries. Try asking about weather or say hi.";
            case "weather":
                return "I don't have live weather data, but looks like a pleasant day!";
            case "exit":
                return "Goodbye! Have a nice day!";
            default:
                return "I'm not sure about that. Could you rephrase?";
        }
    }

    // Main Chat Loop
    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n🤖 Java NLP Chatbot Ready (MCA Level Project)!");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine();

            List<String> lemmas = getLemmas(input);
            String intent = classifyIntent(lemmas);
            String response = generateResponse(intent);

            System.out.println("Bot: " + response);

            if (intent.equals("exit"))
                break;
        }
    }

    public static void main(String[] args) {
        new Chatbot().start();
    }
}
