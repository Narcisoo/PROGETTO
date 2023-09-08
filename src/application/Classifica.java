package application;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Classifica {

	public Classifica() {
	}

	public static void aggiornaClassifica(String playerName, int score) throws IOException {
		Map<String, Integer> scores = leggiPunteggi();
		scores.put(playerName, scores.getOrDefault(playerName, 0) + score);
		scriviPunteggiSuFile(scores);
	}

	private static Map<String, Integer> leggiPunteggi() throws IOException {
		
		Map<String, Integer> scores = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/Highscore.csv"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				scores.put(parts[0], Integer.parseInt(parts[1]));
			}
			reader.close();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return scores;
	}

	private static void scriviPunteggiSuFile(Map<String, Integer> scores) throws IOException {
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter("src/Highscore.csv"));
			for (Map.Entry<String, Integer> entry : scores.entrySet()) {
				writer.write(entry.getKey() + "," + entry.getValue());
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}


