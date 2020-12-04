package io.github.abradat.ps5news.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import io.github.abradat.ps5news.model.TypeSentiment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class SentimentAnalyzer {

    StanfordCoreNLP coreNLP;
    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        coreNLP = new StanfordCoreNLP(properties);
    }

    public TypeSentiment analyzeSentiment(String text) {
        int mainSentiment = 0;

        if(text != null && text.length() > 0) {
            int longest = 0;
            Annotation annotation = coreNLP.process(text);

            for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if(partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }

        return TypeSentiment.fromIndex(mainSentiment);
    }
}
