package com.mazindere.university_reviews_app.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapingService {

    // Map to store the mapping of university IDs to their respective URLs for program information
    private static final Map<String, String> universityProgramUrls = new HashMap<>();

    // Map to store the CSS selectors needed to extract program information from each university's website
    private static final Map<String, String> universityCssSelectors = new HashMap<>();

    public ScrapingService() {
        initializeUniversityData();
    }

    private void initializeUniversityData() {
        // URLs for program information
        universityProgramUrls.put("mmu", "https://www.mmu.ac.ke/faculties-and-academic-programmes/");
        universityProgramUrls.put("ku", "https://www.ku.ac.ke/index.php/academics/schools-departments");
        universityProgramUrls.put("uon", "https://uonbi.ac.ke/faculties-and-departments");
        universityProgramUrls.put("tuk", "https://intake.tukenya.ac.ke/");
        universityProgramUrls.put("chuka", "https://www.chuka.ac.ke/index.php/academics/academic-programmes");
        universityProgramUrls.put("egerton", "https://www.egerton.ac.ke/academic-programmes");
        universityProgramUrls.put("jkuat", "https://www.jkuat.ac.ke/programmes/");
        universityProgramUrls.put("maseno", "https://maseno.ac.ke/index.php/academics/programmes");
        universityProgramUrls.put("meru", "https://www.must.ac.ke/academic-programmes");
        universityProgramUrls.put("kisii", "https://www.kisiiuniversity.ac.ke/academics");

        // CSS Selectors for each university website
        // These might need adjustment based on each university's actual HTML structure
        universityCssSelectors.put("mmu", ".gdlr-core-text-box-item-content");
        universityCssSelectors.put("ku", ".no-left block com_content");
        universityCssSelectors.put("uon", ".content full");
        universityCssSelectors.put("tuk", ".tabs-above");
        universityCssSelectors.put("chuka", ".programmes-list");
        universityCssSelectors.put("egerton", ".academic-programmes");
        universityCssSelectors.put("jkuat", ".programmes-listing");
        universityCssSelectors.put("maseno", ".academic-programs");
        universityCssSelectors.put("meru", ".programmes");
        universityCssSelectors.put("kisii", ".academic-programs");
    }

    /**
     * Fetches and parses program information from a university's website
     *
     * @param universityId The ID of the university
     * @return List of program names or descriptions
     */
    public static List<String> scrapeUniversityPrograms(String universityId) {
        List<String> programs = new ArrayList<>();
        String url = universityProgramUrls.get(universityId);
        String cssSelector = universityCssSelectors.get(universityId);

        if (url == null || cssSelector == null) {
            return programs; // Return empty list if URL or selector not found
        }

        try {
            // Connect to the website and get the HTML document
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

            // Extract the program information based on the CSS selector
            Elements programElements = doc.select(cssSelector);

            // Process based on university structure
            switch (universityId) {
                case "mmu":
                    // MMU has a specific structure for programs
                    for (Element element : programElements) {
                        // Find all list items within the content
                        Elements listItems = element.select("li");
                        for (Element item : listItems) {
                            programs.add(item.text());
                        }

                        // If there are no list items, try to get headings
                        if (programs.isEmpty()) {
                            Elements headings = element.select("h3, h4");
                            for (Element heading : headings) {
                                programs.add(heading.text());
                            }
                        }
                    }
                    break;

                // Add special handling for other universities as needed
                default:
                    // Generic approach for other universities
                    for (Element element : programElements) {
                        // Try to find program names in different HTML structures
                        Elements programNames = element.select("h3, h4, li, .program-name");
                        for (Element program : programNames) {
                            programs.add(program.text());
                        }

                        // If still empty, just get the text
                        if (programs.isEmpty() && !element.text().isEmpty()) {
                            programs.add(element.text());
                        }
                    }
                    break;
            }

        } catch (IOException e) {
            System.err.println("Error scraping university programs: " + e.getMessage());
            // In a production app, use proper logging instead of System.err
        }

        return programs;
    }

    /**
     * Retrieves the raw HTML content of the program section
     * Useful for debugging or more detailed parsing
     */
    public String getRawProgramHtml(String universityId) {
        String url = universityProgramUrls.get(universityId);
        String cssSelector = universityCssSelectors.get(universityId);

        if (url == null || cssSelector == null) {
            return "URL or selector not found for university: " + universityId;
        }

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

            Elements programElements = doc.select(cssSelector);
            if (!programElements.isEmpty()) {
                return programElements.outerHtml();
            } else {
                return "No content found using selector: " + cssSelector;
            }

        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Get the URL for program information for a specific university
     */
    public static String getProgramUrl(String universityId) {
        return universityProgramUrls.getOrDefault(universityId, "");
    }

    /**
     * Updates the URL for a university's program information
     */
    public void updateProgramUrl(String universityId, String newUrl) {
        universityProgramUrls.put(universityId, newUrl);
    }

    /**
     * Updates the CSS selector for a university's program information
     */
    public void updateCssSelector(String universityId, String newSelector) {
        universityCssSelectors.put(universityId, newSelector);
    }


    private static boolean containsRelevantKeyword(String text) {
        String lower = text.toLowerCase();
        return lower.contains("faculty") ||
                lower.contains("department") ||
                lower.contains("school of") ||
                lower.contains("programmes");
    }

}