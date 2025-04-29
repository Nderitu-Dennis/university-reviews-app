package com.mazindere.university_reviews_app.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ScrapingService {
    private final Map<String, String> universityProgramUrls = new HashMap<>();
    private final Map<String, String> universityCssSelectors = new HashMap<>();

    public ScrapingService() {
        initializeUniversityData();
    }

    private void initializeUniversityData() {
        universityProgramUrls.put("mmu", "https://www.mmu.ac.ke/faculties-and-academic-programmes/");
        universityProgramUrls.put("ku", "https://www.ku.ac.ke/index.php/academics/schools-departments");
        universityProgramUrls.put("uon", "https://uonbi.ac.ke/faculties-and-departments");
        universityProgramUrls.put("tuk", "https://intake.tukenya.ac.ke/");
        universityProgramUrls.put("chuka", "https://www.chuka.ac.ke/academics-home-page/");
        universityProgramUrls.put("egerton", "https://www.egerton.ac.ke/academics/faculties");
        universityProgramUrls.put("jkuat", "https://www.jkuat.ac.ke/division/academic/?page_id=17228");
        universityProgramUrls.put("maseno", "https://programmes.maseno.ac.ke/all_programmes");
        universityProgramUrls.put("meru", "https://www.must.ac.ke/#");
        universityProgramUrls.put("kisii", "https://www.kisiiuniversity.ac.ke/academics");

        universityCssSelectors.put("mmu", ".gdlr-core-text-box-item-content");
        universityCssSelectors.put("ku", ".col-md-9");
        universityCssSelectors.put("uon", ".content full");
        universityCssSelectors.put("tuk", ".tabs-above");
        universityCssSelectors.put("chuka", ".programmes-list");
        universityCssSelectors.put("egerton", ".sppb-row-container");
        universityCssSelectors.put("jkuat", ".content_wrap fullwidth");
        universityCssSelectors.put("maseno", ".node__content.clearfix");
        universityCssSelectors.put("meru", ".second.inner");
        universityCssSelectors.put("kisii", ".academic-programs");
    }

    public List<String> scrapeUniversityPrograms(String universityId) {
        List<String> programs = new ArrayList<>();
        String url = universityProgramUrls.get(universityId);
        String cssSelector = universityCssSelectors.get(universityId);

        if (url == null || cssSelector == null) {
            return programs;
        }

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements programElements = doc.select(cssSelector);

            switch (universityId) {
                case "mmu":
                    for (Element element : programElements) {
                        Elements listItems = element.select("li");
                        for (Element item : listItems) {
                            programs.add(item.text());
                        }

                        if (programs.isEmpty()) {
                            Elements headings = element.select("h3, h4");
                            for (Element heading : headings) {
                                programs.add(heading.text());
                            }
                        }
                    }
                    break;

                default:
                    for (Element element : programElements) {
                        Elements programNames = element.select("h3, h4, li, .program-name");
                        for (Element program : programNames) {
                            programs.add(program.text());
                        }

                        if (programs.isEmpty() && !element.text().isEmpty()) {
                            programs.add(element.text());
                        }
                    }
                    break;
            }

        } catch (IOException e) {
            System.err.println("Error scraping university programs: " + e.getMessage());
        }

        return programs;
    }

    public String getRawProgramHtml(String universityId) {
        String url = universityProgramUrls.get(universityId);
        String cssSelector = universityCssSelectors.get(universityId);

        if (url == null || cssSelector == null) {
            return "URL or selector not found for university: " + universityId;
        }

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
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

    public String getProgramUrl(String universityId) {
        return universityProgramUrls.getOrDefault(universityId, "");
    }

    public void updateProgramUrl(String universityId, String newUrl) {
        universityProgramUrls.put(universityId, newUrl);
    }

    public void updateCssSelector(String universityId, String newSelector) {
        universityCssSelectors.put(universityId, newSelector);
    }
}