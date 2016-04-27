package PedroTool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static ArrayList<String> NameTests = new ArrayList<String>();
	

	public static Elements parser(String url) {

//Pobieramy zawarosc stronki w postaci HTML
		
		StringBuilder a = new StringBuilder(); 
		try {
			URL yahoo = new URL(url);
			URLConnection yc = yahoo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				a.append(inputLine);
			in.close();

		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
//Dzieki metoda z biblioteki Jsoup parsujemy wczytana zawartosc HTML i wybieramy z tabeli reportsTable wiersze, 
//jednoczesnie przypisujac je do zmiennej elements typu Element.
		Document doc = Jsoup.parse(a.toString());
		Elements elements = doc.select("#reportsTable").select("tr");

		return elements;
	}

	public static void PickUpElements(Elements elements) {

// przelatujemy po wszystkich wierszach pobranych z tabeli i wybieramy z niej nazwy tych testow ktore sa fail
		for (Element e : elements) {

			String Result = e.select("div").get(3).html().toString();

			if (Result.equals("Failed") || Result.equals("Missing results")) {

				NameTests.add(e.select("div").get(0).select("a").select("nobr").html().toString());
				System.out.println(e.select("div").get(0).select("a").select("nobr").html().toString());
			}
		}

	}

	public static void main(String [ ] args)
	{
		PickUpElements(parser("https://lte-dailyatest.rnd.ki.sw.ericsson.se/log/personal/bbijenkins/jenkins/20160427_020826_3Jy6/summaryReport.html"));
	}
}