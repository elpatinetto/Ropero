package app.ropero.com.ropero;

import java.util.Arrays;
import java.util.List;

/**
 * Created by noellodou on 04/08/2017.
 */

public class CoursesProvider {
    public List<Courses> readCourses() {
        return Arrays.asList(
                new Courses("Soirée chez Alex", 12.43),
                new Courses("Barbecue avec Chris, boy fire",100.00),
                new Courses("Cremaillère chez david zon", 32.45),
                new Courses("Soirée Foot, Inter vs MILAN", 45.00),
                new Courses("Gaming night with my boys", 23.65),
                new Courses("Soirée girls alcool pyjama", 77.69),
                new Courses("Apero wine and saucisson", 98.09)
        );
    }
}
