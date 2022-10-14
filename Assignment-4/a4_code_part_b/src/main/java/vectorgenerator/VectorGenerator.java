package vectorgenerator;
import com.google.api.gax.paging.Page;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.storage.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public final class VectorGenerator implements BackgroundFunction<VectorGenerator.GCSEvent> {
  private static final String TRAIN_VECTOR_CSV = "trainVectors.csv";
  private static final String TRAIN_DATA_BUCKET = "traindatab00899516";

  /**
   * Reference: https://www.baeldung.com/java-levenshtein-distance
   * Reference: https://www.baeldung.com/java-csv
   */
  @Override
  public void accept(GCSEvent event, Context context) {
    Storage storage = StorageOptions.getDefaultInstance().getService();
    Page<Blob> blobs = storage.get(event.bucket).list();
    ArrayList<String[]> dataLinesList = new ArrayList<>();
    StringBuilder listOfWords = new StringBuilder();

    for (Blob blob : blobs.iterateAll()) {
      String txtFileContent = regex(blob);

      ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(txtFileContent.toLowerCase().split(" ")));
      wordsList.removeAll(Arrays.asList("she", "her", "hers", "herself",
              "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what",
              "which", "before", "after", "above", "below",
              "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
              "further", "then", "once", "here", "there", "when", "where", "why", "how", "all",
              "any", "both", "each", "few", "more", "who", "whom", "this", "that", "these", "those", "am", "is", "are",
              "was", "were", "be", "been", "being", "have", "has", "had", "having", "do",
              "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because",
              "as", "until", "while", "of", "at", "by", "for", "with", "about", "against",
              "between", "into", "through", "during", "most", "other", "some", "such", "no",
              "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t",
              "can", "will", "just", "don", "should", "now"));
      for (String word : wordsList) {
        listOfWords.append(word).append(" ");
      }
      /**
       * Reference: https://www.baeldung.com/java-csv
       */
      for (int i = 0; i < wordsList.size() - 1; i++) {
        String currentWord = wordsList.get(i);
        String nextWord = wordsList.get(i + 1);
        int[][] dp = new int[currentWord.length() + 1][nextWord.length() + 1];
        for (int i1 = 0; i1 <= currentWord.length(); i1++) {
          for (int j = 0; j <= nextWord.length(); j++) {
            if (i1 == 0) {
              dp[i1][j] = j;
            } else if (j == 0) {
              dp[i1][j] = i1;
            } else {
              /**
               * Reference: https://www.baeldung.com/java-levenshtein-distance
               */
              dp[i1][j] = Arrays.stream(new int[]{dp[i1 - 1][j - 1] +
                      (currentWord.charAt(i1 - 1) == nextWord.charAt(j - 1) ? 0 : 1), dp[i1 - 1][j] + 1, dp[i1][j - 1] + 1}).min().orElse(Integer.MAX_VALUE);
            }
          }
        }
        int distance = dp[currentWord.length()][nextWord.length()];
        dataLinesList.add(new String[]{currentWord, nextWord, String.valueOf(distance)});
      }
    }
    StringBuilder sb = new StringBuilder();
    for (String[] strings : dataLinesList) {
      sb.append(Stream.of(strings)
              .map(data -> {
                /**
                 * Reference: https://www.baeldung.com/java-csv
                 */
                String escapedData = data.replaceAll("\\R", " ");
                if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                  data = data.replace("\"", "\"\"");
                  escapedData = "\"" + data + "\"";
                }
                return escapedData;
              })
              .collect(Collectors.joining(","))).append("\n");
    }
    if (isbucketNull(storage)) {
      storage.create(BucketInfo.of(TRAIN_DATA_BUCKET));
    } else if (isBucketExists(storage)) {
      storage.create(BucketInfo.of(TRAIN_DATA_BUCKET));
    } else {
    }
    storage.create(BlobInfo.newBuilder(BlobId.of(TRAIN_DATA_BUCKET, TRAIN_VECTOR_CSV)).build(), sb.toString().getBytes());
  }

  /**
   * Reference: https://www.baeldung.com/java-csv
   */
  private String regex(Blob blob) {
    return new String(blob.getContent())
            .replaceAll("[,.\\-_'\"(){}?!@#$%^&*\\[\\]]+", "");
  }

  private boolean isBucketExists(Storage storage) {
    return !storage.get(TRAIN_DATA_BUCKET).exists();
  }

  private boolean isbucketNull(Storage storage) {
    return storage.get(TRAIN_DATA_BUCKET) == null;
  }

  public static class GCSEvent {
    String bucket;
    String name;
    String metaGeneration;

    public GCSEvent(final String bucket,
                    final String name,
                    final String metaGeneration) {
      this.bucket = bucket;
      this.name = name;
      this.metaGeneration = metaGeneration;
    }
  }

}