import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private List<ScheduleEntry> schedule;

    public ScheduleManager() {
        this.schedule = new ArrayList<>();
    }

    public void addEntry(ScheduleEntry entry) {
        schedule.add(entry);
    }

    public void editEntry(ScheduleEntry oldEntry, ScheduleEntry newEntry) {
        int index = schedule.indexOf(oldEntry);
        if (index != -1) {
            schedule.set(index, newEntry);
        }
    }

    public List<ScheduleEntry> getSchedule() {
        return schedule;
    }
}
