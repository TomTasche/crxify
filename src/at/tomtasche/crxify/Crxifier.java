package at.tomtasche.crxify;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Crxifier {

	public static void main(String[] args) {
		String url = "https://github.com/TomTasche/Announcify.js/zipball/master";
		String id = UUID.randomUUID().toString();
		
		try {
			Process process = Runtime.getRuntime().exec("wget -O " + id + ".zip " + url);
			
			File file = new File(id);
			file.mkdir();
			
			try {
				process.waitFor();
			} catch (InterruptedException e) {}
			
			process = Runtime.getRuntime().exec("unzip " + id + ".zip -d " + id);
			
			try {
				process.waitFor();
			} catch (InterruptedException e) {}
			
			file = new File(id + ".zip");
			file.delete();
			
			file = new File(id);
			
			process = Runtime.getRuntime().exec("./crxi " + file.listFiles()[0] + " pem.pem " + id);
			
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			file = new File(id);
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
