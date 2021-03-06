import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This Java source file was auto generated by running 'gradle buildInit --type java-library'
 * by 'Alec.Tunbridge' at '31/03/16 16:08' with Gradle 1.10
 *
 * @author Alec.Tunbridge, @date 31/03/16 16:08
 */
public class WordChains {
    public static List<String> readFile() throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("words.txt").toURI());
        return Files.lines(path, Charset.forName("ISO-8859-1")).collect(Collectors.toList());
    }

    public static void main(String [] args) throws IOException, URISyntaxException {
        List<String> input = new ArrayList<>();
        System.out.println(wordList(input, "open", "part", readFile().stream()
                .filter(s -> s.length() == 4)
                .filter(s1 -> s1.toLowerCase().equals(s1))
                .collect(Collectors.toList())));
    }

    private static List<String> wordList(List<String> accumulator, String start, String end, List<String> dictionary){
        System.out.println(accumulator.size());
        List<String> stopSideEffects = accumulator.stream().collect(Collectors.toList());
        if(start.equals(end)){
            stopSideEffects.add(start);
            return stopSideEffects;
        }

        List<String> nextLevel = findNextLevel(dictionary, start);

        if(nextLevel.size() == 0){
            return stopSideEffects;
        }

        dictionary.removeAll(nextLevel);

        stopSideEffects.add(start);
        for(String word : nextLevel){
            List<String> chain = wordList(stopSideEffects, word, end, dictionary);
            if(chain.size() > stopSideEffects.size()){
                return chain;
            }
        }
        stopSideEffects.remove(start);
        return stopSideEffects;
    }

    private static List<String> findNextLevel(List<String> dictionary, String word) {
        return dictionary.stream().filter((d) -> wordIsOneChangeDifferent(d, word)).collect(Collectors.toList());
    }

    private static boolean wordIsOneChangeDifferent(String word1, String word2){
        if(word1.length() != word2.length()){
            return false;
        }
        int differences = 0;
        int length = word1.length();
        for(int i = 0;i<length;i++){
            if(word1.charAt(i) != word2.charAt(i)){
                differences++;
            }
            if(differences > 1){
                return false;
            }
        }
        return differences == 1;
    }
}
