package ua.com.epam.project.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.Performance;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Util class to generate pdf for course with grades.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class GeneratePdfCourseWithGrades {

    private GeneratePdfCourseWithGrades() {
    }

    /**
     * Function generate pdf with course and it`s grades
     *
     * @param courseDto course
     * @param students  array of students
     * @param userDto   user
     * @param topics    array of topics
     * @return return pdf like ByteArrayOutputStream view
     * @throws DocumentException exception can be thrown
     * @throws IOException       exception can be thrown
     */
    public static ByteArrayOutputStream getPdf(CourseDto courseDto, List<UserDto> students, UserDto userDto, List<Topic> topics) throws DocumentException, IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Document document = new Document();

        BaseFont bf = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 11, Font.NORMAL);

        PdfWriter.getInstance(document, bout);
        PdfPTable table = new PdfPTable(7);
        PdfPTable table2 = new PdfPTable(topics.size() + 2);

        table.setWidthPercentage(100f);
        table.setSpacingBefore(10f);
        table2.setWidthPercentage(90f);
        table2.setSpacingBefore(10f);

        addTableHeader(table, font);
        addTableHeader(table2, topics, font);
        addRows(table, courseDto, font);
        addRows(table2, students, userDto, font);
        document.open();

        Chunk chunk = new Chunk("Course info:", font);
        Chunk chunk2 = new Chunk("Course progress:", font);
        Chunk chunk3 = new Chunk("Students on the course: " + students.size(), font);
        document.add(chunk);
        document.add(new Paragraph("\n"));
        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(chunk2);
        document.add(new Paragraph("\n"));
        document.add(table2);
        document.add(new Paragraph("\n"));
        document.add(chunk3);
        document.close();
        return bout;
    }

    /**
     * Procedure to add header to the table
     *
     * @param table table
     * @param font  font for cells
     */
    private static void addTableHeader(PdfPTable table, Font font) {
        String[] headers = new String[]{"Name", "Date started", "Date ended", "Description", "Created", "Status", "Teacher"};
        Stream.of(headers)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    /**
     * Procedure to add header to the table
     *
     * @param table  table
     * @param topics arrays of topics
     * @param font   font
     */
    private static void addTableHeader(PdfPTable table, List<Topic> topics, Font font) {
        PdfPCell header2 = new PdfPCell();
        header2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header2.setBorderWidth(2);
        header2.setPhrase(new Phrase("Topics/\nStudents", font));
        table.addCell(header2);

        topics.stream().map(Topic::getName).collect(Collectors.toList())
                .forEach(columnTitle -> {
                    header2.setPhrase(new Phrase(String.valueOf(columnTitle), font));
                    table.addCell(header2);
                });
        header2.setPhrase(new Phrase("AVG", font));
        table.addCell(header2);
    }

    /**
     * Procedure to add rows to the table
     *
     * @param table     table
     * @param courseDto course
     * @param font      font
     */
    private static void addRows(PdfPTable table, CourseDto courseDto, Font font) {
        fillRows(table, courseDto, font);
        Phrase phrase;
        table.addCell(courseDto.getCreated().toString());
        phrase = new Phrase(courseDto.getStatus(), font);
        table.addCell(phrase);
        phrase = new Phrase(courseDto.getTeacherLogin(), font);
        table.addCell(phrase);
    }

    /**
     * Procedure to fill to the table
     *
     * @param table     table
     * @param courseDto course
     * @param font      font
     */
    static void fillRows(PdfPTable table, CourseDto courseDto, Font font) {
        Phrase phrase = new Phrase(courseDto.getName(), font);
        table.addCell(phrase);
        table.addCell(courseDto.getDateStart().toString());
        table.addCell(courseDto.getDateEnd().toString());
        phrase = new Phrase(courseDto.getDescription(), font);
        table.addCell(phrase);
    }

    /**
     * Procedure to add rows to the table
     *
     * @param table    table
     * @param students students
     * @param userDto  user
     * @param font     font
     */
    private static void addRows(PdfPTable table, List<UserDto> students, UserDto userDto, Font font) {
        if (userDto != null) {
            fillRows(table, font, userDto);
        } else {
            for (UserDto userDto2 : students) {
                fillRows(table, font, userDto2);
            }
        }
    }

    /**
     * Procedure to fill rows to the table
     *
     * @param table   table
     * @param font    font
     * @param userDto user
     */
    private static void fillRows(PdfPTable table, Font font, UserDto userDto) {
        Phrase phrase = new Phrase(userDto.getLastName() + " " + userDto.getFirstName(), font);
        table.addCell(phrase);
        for (Performance performance : userDto.getPerformanceList()) {
            table.addCell(String.valueOf(performance.getGrade()));
        }
        table.addCell(String.valueOf(userDto.getAverageGrade()));
    }
}