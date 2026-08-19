package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

public class NumberRangeImplementationTest {

    private final NumberRangeImplementation summarizer = new NumberRangeImplementation();

    //collect() method tests

    @Test
    void collect_emptyString_returnsEmptyList() {
        Collection<Integer> result = summarizer.collect("");
        assertTrue(result.isEmpty());
    }

    @Test
    void collect_null_returnsEmptyList() {
        Collection<Integer> result = summarizer.collect(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void collect_singleNumber_returnsSingleElementList() {
        Collection<Integer> result = summarizer.collect("5");
        assertEquals(List.of(5), result);
    }

    @Test
    void collect_sortsUnorderedInput() {
        Collection<Integer> result = summarizer.collect("5,1,3,2,4");
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    void collect_handlesNegativeNumbers() {
        Collection<Integer> result = summarizer.collect("-3,-1,0,2");
        assertEquals(List.of(-3, -1, 0, 2), result);
    }

    @Test
    void collect_numberExceedsIntRange_throwsIllegalArgumentException() {
        // Integer.MAX_VALUE is 2147483647, so this has one too many digits
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("99999999999"));
    }

    @Test
    void collect_numberJustAboveIntMax_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("2147483648")); // MAX_VALUE + 1
    }

    @Test
    void collect_numberJustBelowIntMin_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("-2147483649")); // MIN_VALUE - 1
    }

    @Test
    void collect_intMaxValue_doesNotThrow() {
        assertDoesNotThrow(() -> summarizer.collect(String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void collect_intMinValue_doesNotThrow() {
        assertDoesNotThrow(() -> summarizer.collect(String.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    void collect_doubleComma_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,,3"));
    }

    @Test
    void collect_leadingComma_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect(",1,2"));
    }

    @Test
    void collect_trailingComma_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,2,"));
    }

    @Test
    void collect_handlesExtraWhitespace() {
        Collection<Integer> result = summarizer.collect(" 1 , 2 , 3 ");
        assertEquals(List.of(1, 2, 3), result);
    }

    //summarizeCollection() method tests

    @Test
    void summarize_emptyCollection_returnsBracketString() {
        Collection<Integer> input = summarizer.collect("");
        assertEquals("", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_singleElement_returnsBracketString() {
        Collection<Integer> input = summarizer.collect("5");
        assertEquals("5", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_allConsecutive_returnsSingleRange() {
        Collection<Integer> input = summarizer.collect("1,2,3,4,5");
        assertEquals("1-5", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_allNonConsecutive_returnsCommaSeparatedSingles() {
        Collection<Integer> input = summarizer.collect("1,3,5,7");
        assertEquals("1,3,5,7", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_twoConsecutiveNumbers_returnsRangeNotSingles() {
        Collection<Integer> input = summarizer.collect("1,2");
        assertEquals("1-2", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_mixedRangesAndSingles_matchesReadmeExample() {
        Collection<Integer> input = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        assertEquals("1,3,6-8,12-15,21-24,31", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_endsWithRange_closesRangeCorrectly() {
        Collection<Integer> input = summarizer.collect("1,2,3");
        assertEquals("1-3", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_endsWithSingleAfterRange_appendsSingle() {
        Collection<Integer> input = summarizer.collect("1,2,3,10");
        assertEquals("1-3,10", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_duplicateNumbers_treatedAsConsecutive() {
        // 1 to 1 has a diff of 0, which is <= 1, so duplicates fold into the range.
        Collection<Integer> input = summarizer.collect("1,1,2,3");
        assertEquals("1-3", summarizer.summarizeCollection(input));
    }

    @Test
    void summarize_invalidInput_throwError() {
        // 1 to 1 has a diff of 0, which is <= 1, so duplicates fold into the range.
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("a,b,c"));
    }

    //determineRange() method tests

    @Test
    void determineRange_sameStartAndEnd_returnsSingleNumber() {
        assertEquals("5", summarizer.determineRange(5, 5));
    }

    @Test
    void determineRange_differentStartAndEnd_returnsDashedRange() {
        assertEquals("5-9", summarizer.determineRange(5, 9));
    }

    //Sample Input Test

    @Test
    void endToEnd_collectThenSummarize_matchesExpectedOutput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collected = summarizer.collect(input);
        assertEquals("1,3,6-8,12-15,21-24,31", summarizer.summarizeCollection(collected));
    }
}