# Number Range Summarizer

A small Java application that collects comma-separated integers, sorts them, and summarizes consecutive numbers as ranges.

For example:

```text
Input: 1,3,6,7,8,12,13,14,15,21,22,23,24,31
Output: 1,3,6-8,12-15,21-24,31
```

## Requirements

- Java Development Kit (JDK) 11 or newer
- Apache Maven 3.6 or newer
- Git, if cloning from GitHub

Check that Java and Maven are installed:

```powershell
java -version
mvn -version
```

## Download the project

Clone the repository and enter its directory:

```powershell
git clone <repository-url>
cd numberrangesummarizer
```

Replace `<repository-url>` with the URL of this GitHub repository.

## Run the tests

Run all unit tests from the project root, the directory containing `pom.xml`:

```powershell
mvn test
```

To remove previous build output and run the tests from a clean build:

```powershell
mvn clean test
```

The tests cover sorting, negative numbers, duplicates, empty input, invalid input, and range summarization.

## Run the application

The sample input is defined in the `main` method. Compile the application with Maven and run the class:

```powershell
mvn compile
java -cp target/classes com.project.NumberRangeImplementation
```

The sample program prints:

```text
[1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31]
1,3,6-8,12-15,21-24,31
```

The application currently uses the sample input in the source code; it does not read numbers from command-line arguments or standard input.

## Compile and run manually

Maven is recommended, but the classes can also be compiled directly from the project root:

```powershell
javac -d target/classes src/main/java/com/project/NumberRangeSummarizer.java src/main/java/com/project/NumberRangeImplementation.java
java -cp target/classes com.project.NumberRangeImplementation
```

Both Java source files must be compiled because `NumberRangeImplementation` implements `NumberRangeSummarizer`.

## Project structure

```text
src/
  main/java/com/project/
    NumberRangeImplementation.java
    NumberRangeSummarizer.java
  test/java/com/project/
    NumberRangeImplementationTest.java
pom.xml
```

## Input rules

- Numbers must be integers separated by commas.
- Whitespace around numbers and commas is allowed.
- Numbers are sorted before summarization.
- Consecutive numbers are written as `start-end`.
- Duplicate numbers are retained by `collect` and are treated as part of the same sequence.
- Empty or `null` input returns an empty collection.
- Malformed input, such as `1,,3` or `1,abc`, throws `IllegalArgumentException`.
