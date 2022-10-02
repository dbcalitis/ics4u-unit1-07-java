/*
* This is a program generates marks
* after reading in 2 text files.
*
* @author  Mr Coxall
* @version 1.0
* @since   2020-01-01
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
* This is the marks program.
*/
final class Marks {

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Marks() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The generateMarks() function.
    *
    * @param arrayOfStudents the collection of students
    * @param arrayOfAssignments the collection of assignments
    * @return the generated marks
    */
    public static void generateMarks(
                    final ArrayList<String> arrayOfStudents, 
                    final ArrayList<String> arrayOfAssignments)
    {
        // Declares Random Class
        Random random = new Random();

        // Gets the number of students and assignments.
        final int studLen = arrayOfStudents.size();
        final int assLen = arrayOfAssignments.size();

        final ArrayList<ArrayList<String>> table = 
                new ArrayList<ArrayList<String>>();
        
        // Creates a new array.
        final ArrayList<String> firstRow = new ArrayList<String>();
        firstRow.add(" ");
        for (int count = 0; count < assLen; count++) {
            firstRow.add(arrayOfAssignments.get(count));
        }

        table.add(firstRow);

        // Formats the 2D array.
        for (int studCount = 0; studCount < studLen; studCount++) {
            final ArrayList<String> studRow = new ArrayList<String>();
            studRow.add(arrayOfStudents.get(studCount));
            
            for (int assCount = 0; assCount < assLen; assCount++) {
                final int mark = (int) Math.floor(
                                random.nextGaussian() * 10 + 75);
                studRow.add(String.valueOf(mark));
            }

            table.add(studRow);
        }
        
        // Creates a .csv file.
        // try {
        File file = new File("marks.csv");
        // } catch (FileAlreadyExistsException e) {
        //    System.out.println("File already exists.");
        // }
        
        // Formatting table into a string.
        String tableStr = "";
        
        /*
        for (int row = 0; row < table.size(); row++) {
            final String line = String.join(",", table.get(row)) + ",\n";
            System.out.println(line);
            tableStr.concat(line);
        }*/

        // Writing to the file.
        try {
            FileWriter fileWriter = new FileWriter("marks.csv", true);
            
            for (int row = 0; row < table.size(); row++) {
                final String line = String.join(",", table.get(row)) + ",\n";
                System.out.println(line);
                fileWriter.write(line);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    /**
    * The starting main() function.
    *
    * @param args No args will be used
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssignments = new ArrayList<String>();
        final Path studentFilePath = Paths.get("./", args[0]);
        final Path assignmentFilePath = Paths.get("./", args[1]);
        final Charset charset = Charset.forName("UTF-8");

        // Reading the list of students from a text file.
        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = null;
            while ((lineStudent = readerStudent.readLine()) != null) {
                listOfStudents.add(lineStudent);
                // System.out.println(lineStudent);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        // Reading the list of assignments from a text file.
        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentFilePath, charset)) {
            String lineAssignment = null;
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssignments.add(lineAssignment);
                // System.out.println(lineAssignment);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        // System.out.println(listOfStudents);
        // System.out.println(listOfAssignments);

        // Output - Formats them into .csv file and 
        // outputs the content of the file

        System.out.println("\nStudent Grade Report CSV Formatter");

        generateMarks(listOfStudents, listOfAssignments);

        System.out.println("\nDone.");
    }
}
