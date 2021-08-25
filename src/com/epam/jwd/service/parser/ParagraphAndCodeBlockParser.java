package com.epam.jwd.service.parser;

import com.epam.jwd.domain.CodeBlock;
import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphAndCodeBlockParser extends BaseParser {
    private static final Logger logger = LogManager.getLogger(ParagraphAndCodeBlockParser.class);
    private static final String TEXT_IS_PARSED_MESSAGE = "Text is parsed";
    private static final String PARAGRAPH_IS_PARSED_MESSAGE = "--- Paragraph is parsed";
    private static final String CODEBLOCK_IS_PARSED_MESSAGE = "*** Code Block is parsed";
    private static final String START_PARSING_MESSAGE = "Parsing Text on Paragraphs and CodeBlocks";
    private static final String PARAGRAPH_CODE_BLOCK_REGEX = "((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^.+)";
    private static final String CODE_BLOCK_REGEX = "((^.+?\\{([\\s\\S]+?)^\\})|^.+=.+;|^\\S+;)";

    private static final Pattern PARAGRAPH_AND_CODE_BLOCK_PATTERN = Pattern.compile(PARAGRAPH_CODE_BLOCK_REGEX, Pattern.MULTILINE);
    private static final Pattern CODE_BLOCK_PATTERN = Pattern.compile(CODE_BLOCK_REGEX, Pattern.MULTILINE);
    private static final String START_PARAGRAPH_PARSING_MESSAGE = "--- Start paragraph parsing ";
    private static final String START_CODEBLOCK_PARSING_MESSAGE = "*** Start codeblock parsing ";

    private List<Text> paragraphs = new LinkedList<>();

    @Override
    public List<Text> split(String text) {
        Matcher paragraphAndCodeBlockMatcher = PARAGRAPH_AND_CODE_BLOCK_PATTERN.matcher(text);
        logger.debug(START_PARSING_MESSAGE);
        while (paragraphAndCodeBlockMatcher.find()) {
            String splitElement = text.substring(paragraphAndCodeBlockMatcher.start(), paragraphAndCodeBlockMatcher.end());
            Matcher codeBlockMatcher = CODE_BLOCK_PATTERN.matcher(splitElement);
            if (codeBlockMatcher.find()) {
                logger.debug(START_CODEBLOCK_PARSING_MESSAGE);
                paragraphs.add(new CodeBlock(splitElement));
                logger.debug(CODEBLOCK_IS_PARSED_MESSAGE);
            } else {
                logger.debug(START_PARAGRAPH_PARSING_MESSAGE);
                linkWith(new SentenceParser());
                Paragraph paragraph = new Paragraph();
                splitNext(splitElement).forEach(paragraph::add);
                paragraphs.add(paragraph);
                logger.debug(PARAGRAPH_IS_PARSED_MESSAGE);
            }
        }
        logger.info(TEXT_IS_PARSED_MESSAGE);
        return paragraphs;
    }
}
