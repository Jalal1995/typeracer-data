package com.example.crawler.service;

import com.example.crawler.entity.Race;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TypeRacerService {

    private final RestTemplate rest;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String rootPage(String email) {
        String url = String.format("https://data.typeracer.com/pit/race_history?user=%s&n=100000", email);
        return rest.getForObject(url, String.class);
    }

    public Optional<List<Race>> races(String email, String fromS, String toS) {
        try {
            List<Race> list = new ArrayList<>();
            String typeRacerRootPage = rootPage(email);
            Document doc = Jsoup.parse(typeRacerRootPage);
            Elements races = doc.select("body .container .main .themeContent");
            Element table = races.select("table").get(0);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                int wpm = Integer.parseInt(extractWpm(cols.get(1).text()));
                String accS = cols.get(2).text();
                Double acc = Double.parseDouble(extractAccuracy(accS));
                String dateS = cols.get(5).text();
                LocalDate date = extractDate(dateS);
                list.add(new Race(date, wpm, acc));
            }
            final LocalDate to = (toS == null || toS.isEmpty()) ? LocalDate.now().plusDays(1) : convertToDate(toS);
            final LocalDate from = (fromS == null || fromS.isEmpty()) ? convertToDate("2000-01-01") : convertToDate(fromS);
            List<Race> collect = list.stream()
                    .filter(race -> race.date.isAfter(from) && race.date.isBefore(to))
                    .sorted(Comparator.comparing(r -> r.date))
                    .collect(Collectors.toList());
            return Optional.of(collect);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    public LocalDate extractDate(String s) {
        if (s.equals("today")) return LocalDate.now();
        String[] sDate = s.split(" ");
        String year = sDate[2];
        String month = extractMonth(s);
        String day = sDate[1].substring(0, sDate[1].length() - 1);
        if (day.length() == 1) day = String.format("0%s", day);
        return LocalDate.parse(String.format("%s-%s-%s", year, month, day), formatter);
    }

    public String extractWpm(String s) {
        return s.substring(0, s.length() - 4);
    }

    public String extractAccuracy(String s) {
        return s.substring(0, s.length() - 1);
    }

    public String extractMonth(String s) {
        String s2 = s.substring(0, 3);
        switch (s2) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
        }
        throw new RuntimeException("something is wrong here");
    }

    public LocalDate convertToDate(String s) {
        return LocalDate.parse(s, formatter);
    }
}







