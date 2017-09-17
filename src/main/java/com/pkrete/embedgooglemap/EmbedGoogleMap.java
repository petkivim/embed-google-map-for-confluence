package com.pkrete.embedgooglemap;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Embeds a Google Map into a page.
 * 
 * @author Petteri Kivimäki
 * 
 */
public class EmbedGoogleMap implements Macro {
	private Map<String, String> languages;
	private Map<String, String> mapTypes;
	private Map<String, String> borderStyles;

	public EmbedGoogleMap() {
		buildLanguagesList();
		buildMapTypesList();
		buildBorderStylesList();
	}

	@Override
	public BodyType getBodyType() {
		return BodyType.NONE;
	}

	@Override
	public OutputType getOutputType() {
		return OutputType.BLOCK;
	}

	@Override
	public String execute(Map<String, String> parameters, String bodyContent,
			ConversionContext conversionContext) throws MacroExecutionException {

		StringBuilder builder = new StringBuilder();
		StringBuilder url = new StringBuilder();

		if (parameters.containsKey("address")
				&& !parameters.get("address").isEmpty()) {
			String width = (parameters.containsKey("width")
					&& parameters.get("width").matches("^\\d+$") ? parameters
					.get("width") : "400");
			String height = (parameters.containsKey("height")
					&& parameters.get("height").matches("^\\d+$") ? parameters
					.get("height") : "300");
			String border = (parameters.containsKey("border")
					&& parameters.get("border").matches("^\\d{1}$") ? parameters
					.get("border") : "0");
			String borderStyle = (parameters.containsKey("border_style")
					&& this.borderStyles.containsKey(parameters
							.get("border_style")) ? this.borderStyles
					.get(parameters.get("border_style")) : "none");
			String borderColor = (parameters.containsKey("border_color") ? parameters
					.get("border_color") : "#000000");
			String mapType = (parameters.containsKey("map_type")
					&& this.mapTypes.containsKey(parameters.get("map_type")) ? this.mapTypes
					.get(parameters.get("map_type")) : "m");
			String language = (parameters.containsKey("language")
					&& this.languages.containsKey(parameters.get("language")) ? this.languages
					.get(parameters.get("language")) : "");
			String zoomLevel = (parameters.containsKey("zoom_level")
					&& parameters.get("zoom_level").matches("^\\d{1,2}$") ? parameters
					.get("zoom_level") : "14");
			String showInfo = (parameters.containsKey("show_info") ? parameters
					.get("show_info") : "true");
			String infoLabel = (parameters.containsKey("info_label") ? parameters
					.get("info_label") : "");
			String addLink = (parameters.containsKey("link") ? parameters
					.get("link") : "false");
			String linkLabel = (parameters.containsKey("link_label") ? parameters
					.get("link_label") : "View Larger Map");
			String https = (parameters.containsKey("https") ? parameters
					.get("https") : "true");
			String linkFull = (parameters.containsKey("link_full") ? parameters
					.get("link_full") : "false");

			if (infoLabel.length() > 0) {
				infoLabel = "(" + infoLabel + ")";
			}

			language = (language.isEmpty() ? "" : "&amp;hl=" + language);
			String info = (showInfo.equals("true") ? "" : "&amp;iwloc=near");

			if (!parameters.get("address").matches("(?i)^http(s|):\\/\\/.+")) {
				url.append("http");
				if (https.equals("true")) {
					url.append("s");
				}
				url.append("://maps.google.com/?q=");
				if (parameters.get("address").matches(
						"^([-+]?\\d{1,2}[.]\\d+),\\s*([-+]?\\d{1,3}[.]\\d+)$")) {
					url.append("loc:");
				}
			} else {
				infoLabel = "";
			}

			url.append(parameters.get("address")).append(infoLabel)
					.append(language);
			url.append("&amp;z=").append(zoomLevel).append("&amp;t=")
					.append(mapType).append(info);

			builder.append("<iframe width='").append(width)
					.append("' height='").append(height).append("' ");
			builder.append("style='border: ").append(border).append("px ")
					.append(borderStyle).append(" ").append(borderColor)
					.append("' ");
			builder.append("src='").append(url).append("&amp;output=embed")
					.append("'></iframe>");

			if (addLink.equals("true")) {
				builder.append("<div><a href='").append(url);
				if (linkFull.equals("true")) {
					builder.append("&amp;output=embed");
				}
				builder.append("' target='new'>").append(linkLabel)
						.append("</a></div>");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	/**
	 * Builds a TreeMap that contains a list of map types and their codes.
	 */
	private void buildMapTypesList() {
		this.mapTypes = new TreeMap<String, String>();
		this.mapTypes.put("normal", "m");
		this.mapTypes.put("satellite", "k");
		this.mapTypes.put("hybrid", "h");
		this.mapTypes.put("terrain", "p");
	}

	/**
	 * Builds a TreeMap that contains a list of border styles and their codes.
	 */
	private void buildBorderStylesList() {
		this.borderStyles = new TreeMap<String, String>();
		this.borderStyles.put("none", "none");
		this.borderStyles.put("hidden", "hidden");
		this.borderStyles.put("dotted", "dotted");
		this.borderStyles.put("dashed", "dashed");
		this.borderStyles.put("solid", "solid");
		this.borderStyles.put("double", "double");
	}

	/**
	 * Builds a TreeMap that contains a list of languages and their codes.
	 */
	private void buildLanguagesList() {
		this.languages = new TreeMap<String, String>();
		this.languages.put("Undefined", "");
		this.languages.put("Arabic", "ar");
		this.languages.put("Basque", "eu");
		this.languages.put("Bengali", "bn");
		this.languages.put("Bulgarian", "bg");
		this.languages.put("Catalan", "ca");
		this.languages.put("Chinese (simplified)", "zh-CN");
		this.languages.put("Chinese (traditional)", "zh-TW");
		this.languages.put("Croatian", "hr");
		this.languages.put("Czech", "cs");
		this.languages.put("Danish", "da");
		this.languages.put("Dutch", "nl");
		this.languages.put("English", "en");
		this.languages.put("English (Australian)", "en-AU");
		this.languages.put("English (Great Britain)", "en-GB");
		this.languages.put("Farsi", "fa");
		this.languages.put("Filipino", "fil");
		this.languages.put("Finnish", "fi");
		this.languages.put("French", "fr");
		this.languages.put("Galician", "gl");
		this.languages.put("German", "de");
		this.languages.put("Greek", "el");
		this.languages.put("Gujarati", "gu");
		this.languages.put("Hebrew", "iw");
		this.languages.put("Hindi", "hi");
		this.languages.put("Hungarian", "hu");
		this.languages.put("Indonesian", "id");
		this.languages.put("Italian", "it");
		this.languages.put("Japanese", "ja");
		this.languages.put("Kannada", "kn");
		this.languages.put("Korean", "ko");
		this.languages.put("Latvian", "lv");
		this.languages.put("Lithuanian", "lt");
		this.languages.put("Malayalam", "ml");
		this.languages.put("Marathi", "mr");
		this.languages.put("Norwegian", "no");
		this.languages.put("Norwegian Nynorsk", "nn");
		this.languages.put("Oriya", "or");
		this.languages.put("Polish", "pl");
		this.languages.put("Portuguese", "pt");
		this.languages.put("Portuguese (Brazil)", "pt-BR");
		this.languages.put("Portuguese (Portugal)", "pt-PT");
		this.languages.put("Romanian", "ro");
		this.languages.put("Romansch", "rm");
		this.languages.put("Russian", "ru");
		this.languages.put("Slovak", "sk");
		this.languages.put("Slovenian", "sl");
		this.languages.put("Serbian", "sr");
		this.languages.put("Spanish", "es");
		this.languages.put("Swedish", "sv");
		this.languages.put("Tagalog", "tl");
		this.languages.put("Tamil", "ta");
		this.languages.put("Telugu", "te");
		this.languages.put("Thai", "th");
		this.languages.put("Turkish", "tr");
		this.languages.put("Ukrainian", "uk");
		this.languages.put("Vietnamese", "vi");
	}
}
