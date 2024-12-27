
///Студенты, группы, расписание. Даны студенты, курсы, группы,
// предметы, преподаватели. Необходимо реализовать механику
// расписания занятий факультета
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleApp extends JFrame {
    private final ScheduleManager scheduleManager;
    private final List<Group> groups;
    private final List<Subject> subjects;
    private final List<Teacher> teachers;
    private JList<String> scheduleList;
    private DefaultListModel<String> listModel;
    private JTextField timeField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Group> groupComboBox;
    private JComboBox<Subject> subjectComboBox;
    private JComboBox<Teacher> teacherComboBox;

    public ScheduleApp() {
        setTitle("Расписание факультета");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        scheduleManager = new ScheduleManager();
        groups = new ArrayList<>();
        subjects = new ArrayList<>();
        teachers = new ArrayList<>();

        // Демо-данные
        setupDemoData();

        // UI-компоненты
        JPanel formPanel = createFormPanel();
        JPanel schedulePanel = createSchedulePanel();

        add(formPanel, BorderLayout.NORTH);
        add(schedulePanel, BorderLayout.CENTER);
    }

    private void setupDemoData() {
        groups.add(new Group("Группа 1"));
        groups.add(new Group("Группа 2"));
        groups.add(new Group("Группа 3"));

        subjects.add(new Subject("Математика"));
        subjects.add(new Subject("Физика"));
        subjects.add(new Subject("Информатика"));
        subjects.add(new Subject("История"));

        teachers.add(new Teacher("Иванов И.И."));
        teachers.add(new Teacher("Петров П.П."));
        teachers.add(new Teacher("Сидоров С.С."));
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        groupComboBox = new JComboBox<>(groups.toArray(new Group[0]));
        subjectComboBox = new JComboBox<>(subjects.toArray(new Subject[0]));
        teacherComboBox = new JComboBox<>(teachers.toArray(new Teacher[0]));
        timeField = new JTextField(10);
        dayComboBox = new JComboBox<>(new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница"});
        monthComboBox = new JComboBox<>(new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"});
        JButton addButton = new JButton("Добавить запись");
        JButton editButton = new JButton("Редактировать запись");

        panel.add(new JLabel("Группа:"));
        panel.add(groupComboBox);
        panel.add(new JLabel("Предмет:"));
        panel.add(subjectComboBox);
        panel.add(new JLabel("Преподаватель:"));
        panel.add(teacherComboBox);
        panel.add(new JLabel("Время:"));
        panel.add(timeField);
        panel.add(new JLabel("День недели:"));
        panel.add(dayComboBox);
        panel.add(new JLabel("Месяц:"));
        panel.add(monthComboBox);
        panel.add(addButton);
        panel.add(editButton);

        addButton.addActionListener(e -> {
            Group group = (Group) groupComboBox.getSelectedItem();
            Subject subject = (Subject) subjectComboBox.getSelectedItem();
            Teacher teacher = (Teacher) teacherComboBox.getSelectedItem();
            String time = timeField.getText();
            String day = (String) dayComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();

            if (group != null && subject != null && teacher != null && !time.isEmpty()) {
                ScheduleEntry entry = new ScheduleEntry(group, subject, teacher, time, day, month);
                scheduleManager.addEntry(entry);
                updateScheduleList();
                JOptionPane.showMessageDialog(this, "Запись успешно добавлена!");
            } else {
                JOptionPane.showMessageDialog(this, "Заполните все поля!");
            }
        });

        editButton.addActionListener(e -> {
            String selectedEntryText = scheduleList.getSelectedValue();
            if (selectedEntryText != null) {
                ScheduleEntry selectedEntry = findScheduleEntryByText(selectedEntryText);
                if (selectedEntry != null) {
                    String time = timeField.getText();
                    String day = (String) dayComboBox.getSelectedItem();
                    String month = (String) monthComboBox.getSelectedItem();

                    selectedEntry.setTime(time);
                    selectedEntry.setDayOfWeek(day);
                    selectedEntry.setMonth(month);
                    scheduleManager.editEntry(selectedEntry, selectedEntry);
                    updateScheduleList();
                    JOptionPane.showMessageDialog(this, "Запись успешно отредактирована!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Выберите запись для редактирования!");
            }
        });

        return panel;
    }

    private JPanel createSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        scheduleList = new JList<>(listModel);
        scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scheduleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedEntryText = scheduleList.getSelectedValue();
                if (selectedEntryText != null) {
                    ScheduleEntry selectedEntry = findScheduleEntryByText(selectedEntryText);
                    if (selectedEntry != null) {
                        timeField.setText(selectedEntry.getTime());
                        dayComboBox.setSelectedItem(selectedEntry.getDayOfWeek());
                        monthComboBox.setSelectedItem(selectedEntry.getMonth());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(scheduleList);

        JButton refreshButton = new JButton("Обновить расписание");

        refreshButton.addActionListener(e -> updateScheduleList());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    private void updateScheduleList() {
        listModel.clear();
        for (ScheduleEntry entry : scheduleManager.getSchedule()) {
            listModel.addElement(entry.toString());
        }
    }

    private ScheduleEntry findScheduleEntryByText(String text) {
        for (ScheduleEntry entry : scheduleManager.getSchedule()) {
            if (entry.toString().equals(text)) {
                return entry;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScheduleApp app = new ScheduleApp();
            app.setVisible(true);
        });
    }
}
