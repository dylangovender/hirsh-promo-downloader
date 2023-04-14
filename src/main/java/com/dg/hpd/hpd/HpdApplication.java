package com.dg.hpd.hpd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HpdApplication {

	public static void main(String[] args) {
		String mainURL = "https://www.hirschs.co.za/specials-and-promotions/clearance-sale";
		Document doc = getDoc(mainURL);
		ArrayList<String> links = getMainPageLinks(doc);
		links.remove("https://www.hirschs.co.za/specials-and-promotions/inactive/clearance-sale/clearance-sale-in-store/carnival");
		ArrayList<String> imageURLs = getImages(links);
	}

	public static ArrayList<String> getImages(ArrayList<String> links) {
		ArrayList<String> imageURLs = new ArrayList<>();
		for(String link : links) {
			Document doc = getDoc(link);
			Elements images = doc.select("img[data-element=desktop_image][src*=A4], img[data-element=desktop_image][src*=A42],img[data-element=desktop_image][src*=A43],img[data-element=desktop_image][src*=A44],img[data-element=desktop_image][src*=A45]");
			for (Element image : images) {
				imageURLs.add(image.absUrl("src"));
			}
		}
		return imageURLs;
	}

	public static ArrayList<String> getMainPageLinks(Document doc) {
		ArrayList<String> strLinks = new ArrayList<>();
		Elements eleLinks = doc.select("a[href^='https://www.hirschs.co.za/specials-and-promotions/inactive/clearance-sale/clearance-sale-in-store/']");
		for (Element link : eleLinks) {
			strLinks.add(link.absUrl("href"));
		}
		return strLinks;
	}

	public static Document getDoc(String url) {
		Document doc = null;
		try{
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			System.out.println("Error getting page");
		}
		return doc;
	}
}
