public class Course {
    String courseID;
    String courseName;
    String section;

    public Course(String courseID, String departmentName, String section) {
        this.courseID = courseID;
        this.courseName = departmentName;
        this.section = section;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}