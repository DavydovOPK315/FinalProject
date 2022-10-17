package ua.com.epam.project.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Util class to generate pdf with several courses.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class GeneratePdfSeveralCourses {

    private GeneratePdfSeveralCourses() {
    }

    /**
     * Function generate pdf with course and it`s grades
     *
     * @param courseList course
     * @param userDto    user
     * @return return pdf like ByteArrayOutputStream view
     * @throws DocumentException exception can be thrown
     * @throws IOException       exception can be thrown
     */
    public static ByteArrayOutputStream getPdf(List<CourseDto> courseList, UserDto userDto) throws DocumentException, IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Document document = new Document();
        BaseFont bf = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 11, Font.NORMAL);
        int size = 7;

        if (userDto.getRole().equals("STUDENT"))
            size = 8;

        PdfWriter.getInstance(document, bout);
        PdfPTable table = new PdfPTable(size);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10f);
        addTableHeader(table, userDto, font);
        addRows(table, courseList, userDto, font);
        document.open();
        Chunk chunk = new Chunk("Enrolled in courses:", font);
        document.add(chunk);
        document.add(new Paragraph("\n"));
        document.add(table);
        document.close();
        return bout;
    }

    /**
     * Procedure to add header to the table
     *
     * @param table   table
     * @param userDto user
     * @param font    font
     */
    private static void addTableHeader(PdfPTable table, UserDto userDto, Font font) {
        String[] headers = new String[]{"Name", "Date started", "Date ended", "Description", "Status", "Teacher", "Number of students"};

        if (userDto.getRole().equals("STUDENT"))
            headers = new String[]{"Name", "Date started", "Date ended", "Description", "Status", "Teacher", "Number of students", "AVG"};

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
     * Procedure to fill rows to the table
     *
     * @param table      table
     * @param courseList course
     * @param userDto    user
     * @param font       font
     */
    private static void addRows(PdfPTable table, List<CourseDto> courseList, UserDto userDto, Font font) {
        for (CourseDto course : courseList) {
            GeneratePdfCourseWithGrades.fillRows(table, course, font);
            Phrase phrase;
            phrase = new Phrase(course.getStatus(), font);
            table.addCell(phrase);
            phrase = new Phrase(course.getTeacherLogin(), font);
            table.addCell(phrase);
            table.addCell(String.valueOf(course.getNumberStudents()));

            if (userDto.getRole().equals("STUDENT"))
                table.addCell(String.valueOf(course.getAverageGrade()));
        }
    }
}