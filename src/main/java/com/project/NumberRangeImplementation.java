package com.project;
import java.util.*;

public class NumberRangeImplementation implements NumberRangeSummarizer
{
    //Collect the input
    //Assume that input only contains integers and are separated by one comma,
    //Extra spaces might be added accidentally or numbers might not be sorted
    //Throw IllegalArgumentException if invalid input format
    //Constraint: -2,147,483,648 <= number <= 2,147,483,647 
    public Collection<Integer> collect(String input)
    {
        //Check if input is empty and return empty list object
        if(input == null || input.isBlank())
        {
            return new ArrayList<>();
        }

        //Validates overall shape: optional whitespace, then a number, then zero or more
        //(comma, number) groups
        //Rejects empty tokens from double/leading/trailing commas
        if (!input.matches("\\s*-?\\d+\\s*(,\\s*-?\\d+\\s*)*")) {
            throw new IllegalArgumentException("Invalid input format: " + input);
        }

        List<Integer> numbersList = new ArrayList<>();
        String [] inputList = input.split(",");

        //Loops through inputList array and converts elements to integers before adding to numbersList
        for(String number : inputList)
        {
            try 
            {
                numbersList.add(Integer.parseInt(number.trim()));
            } 
            catch (NumberFormatException e) 
            {
                throw new IllegalArgumentException("Number out of range: " + number, e);
            }
        }

        Collections.sort(numbersList);
        return numbersList;
    }

    public String summarizeCollection(Collection<Integer> input)
    {
        int inputSize = input.size();
        if (inputSize == 0) return "";

        List<Integer> numbers = new ArrayList<>(input);
        if (inputSize == 1) return String.valueOf(numbers.get(0));

        //Use StringBuilder rather than String as more efficient due to mutability
        StringBuilder result = new StringBuilder();

        //Track the start of the current run and the last number seen
        int rangeStart = numbers.get(0);
        int previousNum = rangeStart;

        for (int i = 1; i < inputSize; i++)
        {
            int current = numbers.get(i);
            if (current - previousNum > 1)
            {
                appendRange(result, rangeStart, previousNum);
                rangeStart = current;
            }
            previousNum = current;
        }
        //Flush whatever range/number is still in progress after the loop ends
        //since there is no next element to trigger the range check above
        appendRange(result, rangeStart, previousNum);

        return result.toString();
    }

    //Appends a single number or "start-end" range to the result,
    //prefixing with a comma if it is not the first entry
    private void appendRange(StringBuilder sb, int start, int end)
    {
        if (sb.length() > 0) sb.append(",");
        sb.append(determineRange(start, end));
    }

    //Returns a single number or "start-end" if the range spans more than one value
    public String determineRange(int start, int end)
    {
        return start == end ? String.valueOf(start) : start + "-" + end;
    }

    //Test that the program produces the correct output for the sample input
    public static void main(String [] args)
    {
        NumberRangeSummarizer numRangeObj = new NumberRangeImplementation();

        String nums = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collect_nums = numRangeObj.collect(nums);

        System.out.println(collect_nums);
        String summarized_list = numRangeObj.summarizeCollection(collect_nums);

        System.out.println(summarized_list);
        //Result: "1, 3, 6-8, 12-15, 21-24, 31"
    }
}
