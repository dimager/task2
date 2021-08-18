package com.epam.jwd.service;

import com.epam.jwd.Main;
import com.epam.jwd.domain.CodeBlock;
import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphAndCodeBlockParser extends Parser {
    private final Pattern paragraphAndCodeBlockPattern = Pattern.compile("((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^.+)", Pattern.MULTILINE);
    private final Pattern codeBlockPattern = Pattern.compile("((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^\\S+;)", Pattern.MULTILINE);
    private List<Text> paragraphs = new LinkedList<>();

    @Override
    public List<Text> split(String text) {
        Matcher paragraphAndCodeBlockMatcher = paragraphAndCodeBlockPattern.matcher(text);
        while (paragraphAndCodeBlockMatcher.find()) {
            Main.logger.debug("Parsing Text on Paragraphs and CodeBlocks");
            String splitElement = text.substring(paragraphAndCodeBlockMatcher.start(), paragraphAndCodeBlockMatcher.end());
            Matcher codeBlockMatcher = codeBlockPattern.matcher(splitElement);
            if (codeBlockMatcher.find()) {
                paragraphs.add(new CodeBlock(splitElement));
                Main.logger.debug("Code Block is parsed");
            } else {
                linkWith(new SentenceParser());
                Paragraph paragraph = new Paragraph();
                splitNext(splitElement).forEach(paragraph::add);
                paragraphs.add(paragraph);
                Main.logger.debug("Paragraph is parsed");
            }
        }
        return paragraphs;
    }
}
