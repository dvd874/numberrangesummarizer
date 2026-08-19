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

    //Produces a comma delimited list of numbers,
    //Grouping the numbers into a range when they are sequential
    public String summarizeCollection(Collection<Integer> input)
    {
        //Return input if it contains less than two elements
        if(input.size() < 2)
        {
            return input.toString();
        }

        List<Integer> numbersList = new ArrayList<>(input);
        List <String> resultList = new ArrayList<>();
        
        //Use range variables to use in determining ranges
        //Use count variable to keep track of current index position in numberslist
        int rangeStart = numbersList.get(0);
        int rangeEnd = numbersList.get(0);
        int count = 0;

        //Loop through list of numbers and determine ranges where sequential
        for(int number : numbersList)
        {
            //A gap greater than 1 means the current number is not consecutive with
            //the previous one so the range being built has ended
            if(number - rangeEnd > 1)
            {
                resultList.add(determineRange(rangeStart, rangeEnd));
                rangeStart = number;
            }

            //Detects the last iteration so the final range/single number gets flushed,
            //since there is no element after it to trigger the end of range check above
            if(count == input.size()-1)
            {
                if(number - rangeEnd > 1)
                {
                    resultList.add(String.valueOf(number));
                }
                else
                {
                    resultList.add(determineRange(rangeStart, number));
                }
            }
            rangeEnd = number;  
            count++;  
        }

        return String.join(",", resultList);
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
