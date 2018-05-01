/*
Steven Prine
CSC-346
Prof. Noynaert
*/


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<String> listings = new ArrayList<>();

    public static void main(String[] args) {
        String fileName = "courses.xml";
        parseXML(fileName);
    }

    public static List<Course> parseXML(String fileName) {
        List<Course> courses = new ArrayList<>();

        //create the factory to create the xmlEventReader we really want.
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            Course course = new Course("", "", "");
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(
                    new FileInputStream(fileName));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {

                    StartElement it = xmlEvent.asStartElement();
                    QName tag = it.getName();
                    String name = tag.getLocalPart();

                    if (name.equalsIgnoreCase("course")) {
                        course = new Course("courseId", "title", "section");

                        //Successfully pulling out the course name
                    } else if (name.equalsIgnoreCase("courseID")) {
                        String s = xmlEventReader.getElementText();
                        listings.add("<" + tag + ">" + s + "</" + name + ">\n");
                        course.setCourseID(s);

                    } else if (name.equalsIgnoreCase("title")) {
                        String s = xmlEventReader.getElementText();
                        listings.add("<" + tag + ">" + s + "</" + name + ">\n");
                        course.setCourseName(s);

                    } else if (name.equalsIgnoreCase("section")) {
                        String s = xmlEventReader.getElementText();
                        listings.add("<" + tag + ">" + s + "</" + name + ">\n");
                        course.setSection(s);
                    }
                }
                if (xmlEvent.isEndElement()) {
                    System.out.println("End Element: " + xmlEvent);
                    EndElement it = xmlEvent.asEndElement();
                    String name = it.getName().getLocalPart();

                    if (name.equalsIgnoreCase("course")) {
                        course = null;
                    }
                }
            }
            print(listings);
            printWriter(listings);


            xmlEventReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(2);

        } catch (javax.xml.stream.XMLStreamException e) {
            e.printStackTrace();
        }
        return courses;
    }


    //Print out any kind of list given.
    public static <T> void print(List<T> list) {
        System.out.println("__________________");
        for (T t : list) {
            System.out.println(t);
        }
        System.out.println("__________________");
    }

    public static <T> void printWriter(List<T> list) {
        try {
            FileWriter writer = new FileWriter("course.xml");
            PrintWriter printWriter = new PrintWriter(writer);

            for (T lists : list) {
                printWriter.write(String.valueOf(lists));
            }
            printWriter.flush();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}