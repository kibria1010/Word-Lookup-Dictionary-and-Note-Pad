package dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

public class Dictionary {
    private final TrieNode root;
    private final Set<String> wordSet;

    public Dictionary() {
        root = new TrieNode();
        wordSet = new HashSet<>();
    }

    // Load dictionary into Trie and HashSet
    public void loadDictionary(List<String> words) {
        for (String word : words) {
            insert(word);
            wordSet.add(word);
        }
    }

    // Insert a word into the Trie
    private void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    // Validate if a word exists in the dictionary
    public boolean isValidWord(String word) {
        return wordSet.contains(word);  // Constant time lookup
    }

    // Find suggestions based on a prefix
    public List<String> getSuggestions(String prefix) {
        List<String> suggestions = new ArrayList<>();
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return suggestions;  // No suggestions if prefix not found
        }
        findAllWordsFromNode(node, new StringBuilder(prefix), suggestions);
        return suggestions;
    }

    private void findAllWordsFromNode(TrieNode node, StringBuilder prefix, List<String> suggestions) {
        if (node.isEndOfWord) suggestions.add(prefix.toString());
        for (char c : node.children.keySet()) {
            findAllWordsFromNode(node.children.get(c), prefix.append(c), suggestions);
            prefix.setLength(prefix.length() - 1);  // backtrack
        }
    }
    
    public static void main(String[] args) {
        List<String> lines=null;
        Dictionary dictionary = new Dictionary();
        try {
            lines = Files.readAllLines(Paths.get("example.txt"));
            for (String line : lines) {
                 dictionary.loadDictionary(Arrays.asList(line));
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        
       
        System.out.println(dictionary.isValidWord("studen"));
        String prefix = "ap";
        List<String> s = dictionary.getSuggestions(prefix);
        System.out.println(s);
        for (String string : s) {
            System.out.println(string);
        }
        
        List<String> suggestions = dictionary.getSuggestions(prefix);
        System.out.println("Suggestions for prefix '" + prefix + "': " + suggestions);
        
    }
}

