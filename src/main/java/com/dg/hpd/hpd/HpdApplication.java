package com.dg.hpd.hpd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HpdApplication {

	public static void main(String[] args) {
		Document doc = getPage();
		ArrayList<String> links = getMainPageLinks(doc);
		System.out.println(links);
	}

	public static ArrayList<String> getMainPageLinks(Document doc) {
		ArrayList<String> strLinks = new ArrayList<>();
		Elements eleLinks = doc.select("a[href^='https://www.hirschs.co.za/specials-and-promotions/inactive/clearance-sale/clearance-sale-in-store/']");
		for (Element link : eleLinks) {
			strLinks.add(link.absUrl("href"));
		}
		return strLinks;
	}

	public static Document getPage() {
		String url = "https://www.hirschs.co.za/specials-and-promotions/clearance-sale";
		Document doc = null;
		try{
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			System.out.println("Error getting page");
		}
		return doc;
	}
}
