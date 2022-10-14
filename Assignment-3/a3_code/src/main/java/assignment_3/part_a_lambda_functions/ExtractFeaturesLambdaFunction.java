package assignment_3.part_a_lambda_functions;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class ExtractFeaturesLambdaFunction implements RequestHandler<S3Event, String> {
    final static String LOG = "assignment_3.part_a_lambda_functions.ExtractFeaturesLambdaFunction";

    public ExtractFeaturesLambdaFunction() throws IOException {
    }
    @Override
    public String handleRequest(final S3Event s3Event,
                                final Context context) {
        String bucketName = null;
        String fileNameKey = null;
        String fileNameEnding=  "ne \":";
        String fileExtention = "ne" + ".txt";
        try {
            // Source Link: https://www.techie-knowledge.co.in/2017/02/removing-stop-words-from-text-using-java.html
            String[] stopWords = {  "a", "about", "above", "across", "after", "again",
                    "against", "all", "almost", "alone", "along", "already", "also",
                    "although", "always", "among", "an", "and", "another", "any",
                    "anybody", "anyone", "anything", "anywhere", "are", "area",
                    "areas", "around", "as", "ask", "asked", "asking", "asks", "at",
                    "away", "b", "back", "backed", "backing", "backs", "be", "became",
                    "behind", "being", "beings", "best", "better", "between", "big",
                    "both", "but", "by", "c", "f", "face", "faces",
                    "fact", "facts", "far", "felt", "few", "find", "finds", "first",
                    "for", "four", "from", "full", "fully", "further", "furthered",
                    "furthering", "furthers", "g", "gave", "general", "generally",
                    "get", "gets", "give", "given", "gives", "go", "going", "good",
                    "goods", "got", "great", "greater", "greatest", "group", "grouped",
                    "grouping", "groups", "h", "had", "has", "have", "having", "he",
                    "her", "here", "herself", "high", "high", "high", "higher",
                    "highest", "him", "himself", "his", "how", "however", "i", "if",
                    "important", "in", "interest", "interested", "interesting",
                    "interests", "into", "is", "it", "its", "itself", "j", "just", "k",
                    "keep", "keeps", "kind", "knew", "know", "known", "knows", "l",
                    "large", "largely", "last", "later", "latest", "least", "less",
                    "let", "lets", "like", "likely", "long", "longer", "longest", "m",
                    "made", "make", "making", "man", "many", "may", "me", "member",
                    "members", "men", "might", "more", "most", "mostly", "mr", "mrs",
                    "much", "must", "my", "myself", "n", "necessary", "need", "needed",
                    "needing", "needs", "never", "new", "new", "newer", "newest",
                    "next", "no", "nobody", "non", "noone", "not", "nothing", "now",
                    "nowhere", "number", "numbers", "o", "of", "off", "often", "old",
                    "older", "oldest", "on", "once", "one", "only", "open", "opened",
                    "opening", "opens", "or", "order", "ordered", "ordering", "orders",
                    "other", "others", "our", "out", "over", "p", "part", "parted",
                    "parting", "parts", "per", "perhaps", "place", "places", "point",
                    "pointed", "pointing", "points", "possible", "present",
                    "presented", "presenting", "presents", "problem", "problems",
                    "put", "puts", "q", "quite", "r", "rather", "really", "right",
                    "right", "room", "rooms", "s", "said", "same", "saw", "say",
                    "says", "second", "seconds", "see", "seem", "seemed", "seeming",
                    "seems", "sees", "several", "shall", "she", "should", "show",
                    "showed", "showing", "shows", "side", "sides", "since", "small",
                    "smaller", "smallest", "so", "some", "somebody", "someone",
                    "something", "somewhere", "state", "states", "still", "still",
                    "such", "sure", "t", "take", "taken", "than", "that", "the",
                    "their", "them", "then", "there", "therefore", "these", "they",
                    "thing", "things", "think", "thinks", "this", "those", "though",
                    "thought", "thoughts", "three", "through", "thus", "to", "today",
                    "together", "too", "took", "toward", "turn", "turned", "turning",
                    "turns", "two", "u", "under", "until", "up", "upon", "us", "use",
                    "used", "uses", "v", "very", "w", "want", "wanted", "wanting",
                    "wants", "was", "way", "ways", "we", "well", "wells", "went",
                    "were", "what", "when", "where", "whether", "which", "while",
                    "who", "whole", "whose", "why", "will", "with", "within",
                    "without", "work", "worked", "working", "works", "would", "x", "y",
                    "year", "years", "yet", "you", "young", "younger", "youngest",
                    "your", "yours", "z", "the" };

            Map<String, Integer> entities = new HashMap<>();
            bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
            fileNameKey = URLDecoder.decode(s3Event.getRecords().get(0).getS3().getObject().getKey().replace('+', ' '), "UTF-8");
            context.getLogger().log(LOG + ": BucketName: " + bucketName + " FileName key: " + fileNameKey);

            String fileContent = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build().getObjectAsString(bucketName, fileNameKey);

            String[] listOfWords = fileContent
                    .replaceAll("\\.", "")
                    .replaceAll(",", "")
                    .replaceAll("\\s+(.*?)", " ")
                    .split(" ");

            ArrayList<String> listOfWordsWithoutStopwords = new ArrayList<>();

            for (int i=0; i< listOfWords.length; i++){
                for (int j=0; j<stopWords.length; j++){
                    if (listOfWords[i].equalsIgnoreCase(stopWords[j])){
                        i++;
                    } else {
                        j++;
                    }
                }
                listOfWordsWithoutStopwords.add(listOfWords[i]);
            }

            context.getLogger().log(LOG + ": " + listOfWordsWithoutStopwords);

            getWordFrequency(entities, listOfWordsWithoutStopwords);

            context.getLogger().log(LOG + ": " + entities);

            String fileName = "\"" + fileNameKey.split("\\.")[0] + fileNameEnding;

            Gson gsonObj = new Gson();
            String jsonStr = fileName + gsonObj.toJson(entities);

            context.getLogger().log(LOG + ": " + jsonStr);

            String newFileName = fileNameKey.split("\\.")[0] + fileExtention;
            AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build().putObject("tagsb00899516", newFileName, jsonStr);
            context.getLogger().log(LOG + ": " + "Content written to file " + newFileName + " successfully!");
            return "correct";
        } catch (UnsupportedEncodingException e) {
            context.getLogger().log(LOG + ": " + e.getMessage());
            return "error";
        }
    }
    private void getWordFrequency(Map<String, Integer> entities, ArrayList<String> listOfWords) {
        for (String word : listOfWords) {
            if (Character.isUpperCase(word.charAt(0))) {
                if (entities.containsKey(word)) {
                    entities.put(word, entities.get(word) + 1);
                } else {
                    entities.put(word, 1);
                }
            }
        }
    }
}
