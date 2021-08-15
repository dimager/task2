package com.epam.jwd.service;

import com.epam.jwd.domain.CodeBlock;
import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphAndCodeBlcokParser extends Parser {
    private final Pattern paragraphAndCodeBlockPattern = Pattern.compile("((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^.+)", Pattern.MULTILINE);
    private final Pattern codeBlockPattern = Pattern.compile("((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^\\S+;)", Pattern.MULTILINE);
    private List<Text> textElementList = new LinkedList();

    @Override
    public List<Text> split(String text) {
        Matcher paragraphAndCodeBlockMatcher = paragraphAndCodeBlockPattern.matcher(text);
        while (paragraphAndCodeBlockMatcher.find()) {
            String splitElement = text.substring(paragraphAndCodeBlockMatcher.start(), paragraphAndCodeBlockMatcher.end());
            Matcher codeBlockMatcher = codeBlockPattern.matcher(splitElement);
            if (codeBlockMatcher.find()) {
                textElementList.add(new CodeBlock(splitElement));
            } else {
                linkWith(new SentenceParser());
                Paragraph paragraph = new Paragraph();
                splitNext(splitElement).forEach(element -> paragraph.add(element));
                textElementList.add(paragraph);
            }

        }
        return textElementList;
    }
}
