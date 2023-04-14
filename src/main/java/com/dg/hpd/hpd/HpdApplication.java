package com.dg.hpd.hpd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;

public class HpdApplication {

	public static void main(String[] args) {
		String mainURL = "https://www.hirschs.co.za/specials-and-promotions/clearance-sale";
		Document doc = getDoc(mainURL);
		ArrayList<String> links = getMainPageLinks(doc);
		links.remove("https://www.hirschs.co.za/specials-and-promotions/inactive/clearance-sale/clearance-sale-in-store/carnival");
		ArrayList<String> imageURLs = getImageURLs(links);
		saveImages(imageURLs);
	}

	public static void saveImages(ArrayList<String> imageURLs) {
		String saveDir = "D:\\IdeaProjects\\hirsh-promo-downloader\\target\\images\\";
		try {
			for(String imageURL : imageURLs) {
				String[] tokens = imageURL.split("/");
				String fileName = tokens[tokens.length-1];
				String savePath = saveDir + fileName;

				URL url = new URL(imageURL);
				BufferedInputStream in = new BufferedInputStream(url.openStream());
				FileOutputStream out = new FileOutputStream(savePath);

				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
				out.close();
				in.close();
				System.out.println("Image saved: " + fileName + " with path: " + savePath);
			}

		} catch (Exception e) {
			System.out.println("Error downloading image: " + e.getMessage());
		}
	}

	public static ArrayList<String> getImageURLs(ArrayList<String> links) {
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
