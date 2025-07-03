package oop.finalexam.t2;

public class Course {
    private String title;
    private String acceptancePrerequisites;
    private String majorTopics;

    public Course(String title, String acceptancePrerequisites, String majorTopics) {
        this.title = title;
        this.acceptancePrerequisites = acceptancePrerequisites;
        this.majorTopics = majorTopics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcceptancePrerequisites() {
        return acceptancePrerequisites;
    }

    public void setAcceptancePrerequisites(String acceptancePrerequisites) {
        this.acceptancePrerequisites = acceptancePrerequisites;
    }

    public String getMajorTopics() {
        return majorTopics;
    }

    public void setMajorTopics(String majorTopics) {
        this.majorTopics = majorTopics;
    }

    @Override
    public String toString() {
        return "Title: " + title +
               "\nAcceptance Prerequisites: " + acceptancePrerequisites +
               "\nMajor Topics: " + majorTopics;
    }
}
