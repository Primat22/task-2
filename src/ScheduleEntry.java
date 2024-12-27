public class ScheduleEntry {
    private Group group;
    private Subject subject;
    private Teacher teacher;
    private String time;
    private String dayOfWeek;
    private String month;

    public ScheduleEntry(Group group, Subject subject, Teacher teacher, String time, String dayOfWeek, String month) {
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
    }

    public Group getGroup() {
        return group;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getTime() {
        return time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMonth() {
        return month;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Группа: " + group.getName() +
                ", Предмет: " + subject.getName() +
                ", Преподаватель: " + teacher.getName() +
                ", Время: " + time +
                ", День недели: " + dayOfWeek +
                ", Месяц: " + month;
    }
}
