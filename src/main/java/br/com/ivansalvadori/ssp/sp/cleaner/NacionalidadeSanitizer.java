package br.com.ivansalvadori.ssp.sp.cleaner;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class NacionalidadeSanitizer {
    private HashMap<String, HashSet<String>> keywords = new HashMap<>();
    private HashMap<String, HashSet<Pattern>> regexps = new HashMap<>();

    private static class RegexpMap {
        public static class RegexSpec {
            public String nacionalidade;
            public String[] regexps;
        }
        public RegexSpec[] specs;
    }


    public NacionalidadeSanitizer() {
        keywords.put("BRASILEIRA", keywordsBrasileira());
        for (RegexpMap.RegexSpec spec : loadRegExps().specs) {
            HashSet<Pattern> set = new HashSet<>();
            for (String string : spec.regexps) set.add(Pattern.compile(string));
            regexps.put(spec.nacionalidade, set);
        }
    }

    private RegexpMap loadRegExps() {
        ClassLoader loader = NacionalidadeSanitizer.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("nacionalidade-regexps.json");
        InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
        return new Gson().fromJson(reader, RegexpMap.class);
    }

    private HashSet<String> keywordsBrasileira() {
        HashSet<String> set = new HashSet<>();
        ClassLoader loader = NacionalidadeSanitizer.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream("estados.csv");
        InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
        CSVReader csvReader = new CSVReader(reader, ',');
        try {
            for (String[] line : csvReader.readAll()) {
                for (String kw : line) set.add(kw.toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException while reading from resource.", e);
        }

        return set;
    }

    public String sanitize(String nacionalidade) {
        nacionalidade = nacionalidade.trim();
        nacionalidade = nacionalidade.toUpperCase();
        nacionalidade = removeRepeatedLetters(nacionalidade);

        for (Map.Entry<String, HashSet<Pattern>> entry : regexps.entrySet()) {
            for (Pattern pattern : entry.getValue()) {
                if (pattern.matcher(nacionalidade).matches())
                    return entry.getKey();
            }
        }
        for (Map.Entry<String, HashSet<String>> entry : keywords.entrySet()) {
            for (String kw : entry.getValue()) {
                if (nacionalidade.equals(kw))
                    return entry.getKey();
            }
        }

        return nacionalidade;
    }

    private String removeRepeatedLetters(String nacionalidade) {
        String old = nacionalidade;
        do {
            old = nacionalidade;
            nacionalidade = nacionalidade.replaceAll("([^SR])\\1", "$1");
        } while (!old.equals(nacionalidade));
        return nacionalidade;
    }
}
