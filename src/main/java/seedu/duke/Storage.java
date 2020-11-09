package seedu.duke;

import seedu.duke.calendar.CalendarItem;
import seedu.duke.calendar.CalendarList;
import seedu.duke.calendar.event.Activity;
import seedu.duke.calendar.event.Event;
import seedu.duke.calendar.event.Exam;
import seedu.duke.calendar.event.Lab;
import seedu.duke.calendar.event.Lecture;
import seedu.duke.calendar.event.Tutorial;
import seedu.duke.calendar.task.Deadline;
import seedu.duke.calendar.task.Task;
import seedu.duke.calendar.task.Todo;
import seedu.duke.resources.ModuleChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the local file used to store the task list data.
 */
//@@author zhangyilin0203
public class Storage {

    private static final int TYPE = 0;
    private static final int TASK_IS_DONE = 1;
    private static final int TASK_DESCRIPTION = 2;
    private static final int TASK_IMPORTANT = 3;
    private static final int TASK_DATE = 4;

    private static final int EVENT_MODULE_CODE = 2;
    private static final int DETAILS = 2;
    private static final int EVENT_IS_OVER = 1;
    private static final int EVENT_DATE = 3;
    private static final int EVENT_TIME = 4;
    private static final int EVENT_VENUE = 5;
    private static final int EVENT_ADDITION_INFO = 6;

    private static ArrayList<CalendarItem> taskArrayList;
    private static String filePath;
    public static int countFileTasks = 0;

    /**
     * Constructor of the Storage class.
     * Initialize file f and file path, if f does not exists, creat a new file f.
     *
     * @param filePath the path that is the destination of the file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates a new storage file if the file does not exists.
     *
     * @param output Storage file.
     */
    private static void createFile(File output) {
        try {
            if (output.exists()) {
                return;
            }
            if (!output.getParentFile().exists()) {
                output.getParentFile().mkdirs();
            }
            output.createNewFile();
        } catch (IOException e) {
            Ui.printFileCreateErrorMessage(e);
        }
    }

    /**
     * Write the data from taskList into file.
     *
     * @param calendarList the calendar list that the data is stored during running the program.
     */
    public static void writeToFile(CalendarList calendarList) {
        try {
            assert filePath != null : "filePath should not be null";
            File output = new File(filePath);
            createFile(output);
            FileWriter fw = new FileWriter(output);
            taskArrayList = calendarList.getCalendarList();
            for (CalendarItem item : taskArrayList) {
                fw.write(item.printIntoFile() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            Ui.printSaveDataErrorMessage(e);
        }
    }

    /**
     * Read data from file and store the data into the taskList.
     *
     * @param calendarList A taskList that store the data read from file.
     */
    public static void readFromFile(CalendarList calendarList) {
        File input = new File(filePath);
        createFile(input);
        Scanner sc = null;
        try {
            sc = new Scanner(input);
        } catch (FileNotFoundException e) {
            Ui.printFileNotFoundMessage();
        }
        while (sc.hasNext()) {
            CalendarItem item = null;
            String[] taskInFile = sc.nextLine().split("\\|");
            int num = taskInFile.length;
            assert taskInFile[TYPE] != null : "the type of the task should not be null";
            item = getItemsFromFile(item, taskInFile, num);
            countFileTasks++;
            addItemToCalendarList(calendarList, item);
            markTaskAsDone(item, taskInFile);
            markEventAsOver(item, taskInFile);
            markTaskAsImportant(item, taskInFile);
            loadAdditionInformation(item, taskInFile, num);
        }
    }

    private static CalendarItem getItemsFromFile(CalendarItem item, String[] taskInFile, int num) {
        switch (taskInFile[TYPE]) {
        case "T":
            if (num == 4) {
                item = new Todo(taskInFile[TASK_DESCRIPTION]);
            } else {
                System.out.println("element in file is lost in " + taskInFile[TASK_DESCRIPTION]);
            }
            break;
        case "D":
            if (num == 5) {
                item = readDeadlineTask(taskInFile);
            } else {
                System.out.println("element in file is lost in deadline: " + taskInFile[TASK_DESCRIPTION]);
            }
            break;
        case "ACT":
            if (num >= 7) {
                item = readActivity(taskInFile);
            } else {
                System.out.println("element in file is lost in act: " + taskInFile[DETAILS]);
            }
            break;
        case "LEC":
            if (num >= 7) {
                item = readLectureEvent(taskInFile);
            } else {
                System.out.println("element in file is lost in lec: " + taskInFile[EVENT_MODULE_CODE]);
            }
            break;
        case "TUT":
            if (num >= 7) {
                item = readTutorialEvent(taskInFile);
            } else {
                System.out.println("element in file is lost in tut: " + taskInFile[EVENT_MODULE_CODE]);
            }
            break;
        case "LAB":
            if (num >= 7) {
                item = readLabEvent(taskInFile);
            } else {
                System.out.println("element in file is lost in lab: " + taskInFile[EVENT_MODULE_CODE]);
            }
            break;
        case "EXAM":
            if (num >= 7) {
                item = readExamEvent(taskInFile);
            } else {
                System.out.println("element in file is lost in exam: " + taskInFile[EVENT_MODULE_CODE]);
            }
            break;
        default:
            Ui.printWrongStorageInput();
            break;
        }
        return item;
    }

    /**
     * Reads exam event to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readExamEvent(String[] taskInFile) {
        CalendarItem item = null;
        LocalTime time;
        LocalDate date;
        if (taskInFile[EVENT_DATE].equals("") || taskInFile[EVENT_TIME].equals("")
                || taskInFile[EVENT_MODULE_CODE].equals("") || taskInFile[EVENT_VENUE].equals("")) {
            System.out.println("element in file is lost in exam: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        if (!isValid(taskInFile)) {
            System.out.println("module code incorrect in exam: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        try {
            date = LocalDate.parse(taskInFile[EVENT_DATE].trim());
            time = LocalTime.parse(taskInFile[EVENT_TIME].trim());
            item = new Exam(taskInFile[EVENT_MODULE_CODE], date, time, taskInFile[EVENT_VENUE]);
        } catch (DateTimeException e) {
            System.out.println("Invalid exam date time " + taskInFile[EVENT_DATE] + " " + taskInFile[EVENT_TIME]
                    + " for " + taskInFile[EVENT_MODULE_CODE]);
        }
        return item;
    }

    /**
     * Reads lab event to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readLabEvent(String[] taskInFile) {
        CalendarItem item = null;
        LocalTime time;
        LocalDate date;
        if (taskInFile[EVENT_DATE].equals("") || taskInFile[EVENT_TIME].equals("")
                || taskInFile[EVENT_MODULE_CODE].equals("") || taskInFile[EVENT_VENUE].equals("")) {
            System.out.println("element in file is lost in lab: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        if (!isValid(taskInFile)) {
            System.out.println("module code incorrect in lab: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        try {
            date = LocalDate.parse(taskInFile[EVENT_DATE].trim());
            time = LocalTime.parse(taskInFile[EVENT_TIME].trim());
            item = new Lab(taskInFile[EVENT_MODULE_CODE], date, time, taskInFile[EVENT_VENUE]);
        } catch (DateTimeException e) {
            System.out.println("Invalid lab date time " + taskInFile[EVENT_DATE] + " " + taskInFile[EVENT_TIME]
                    + " for " + taskInFile[EVENT_MODULE_CODE]);
        }
        return item;
    }

    /**
     * Reads tutorial event to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readTutorialEvent(String[] taskInFile) {
        CalendarItem item = null;
        LocalTime time;
        LocalDate date;
        if (taskInFile[EVENT_DATE].equals("") || taskInFile[EVENT_TIME].equals("")
                || taskInFile[EVENT_MODULE_CODE].equals("") || taskInFile[EVENT_VENUE].equals("")) {
            System.out.println("element in file is lost in tut: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        if (!isValid(taskInFile)) {
            System.out.println("module code incorrect in tutorial: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        try {
            date = LocalDate.parse(taskInFile[EVENT_DATE].trim());
            time = LocalTime.parse(taskInFile[EVENT_TIME].trim());
            item = new Tutorial(taskInFile[EVENT_MODULE_CODE], date, time, taskInFile[EVENT_VENUE]);
        } catch (DateTimeException e) {
            System.out.println("Invalid tutorial date time " + taskInFile[EVENT_DATE] + " " + taskInFile[EVENT_TIME]
                    + " for " + taskInFile[EVENT_MODULE_CODE]);
        }
        return item;
    }

    /**
     * Reads lecture event to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readLectureEvent(String[] taskInFile) {
        CalendarItem item = null;
        LocalTime time;
        LocalDate date;
        if (taskInFile[EVENT_DATE].equals("") || taskInFile[EVENT_TIME].equals("")
                || taskInFile[EVENT_MODULE_CODE].equals("") || taskInFile[EVENT_VENUE].equals("")) {
            System.out.println("element in file is lost in lec: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        if (!isValid(taskInFile)) {
            System.out.println("module code incorrect in lec: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        try {
            date = LocalDate.parse(taskInFile[EVENT_DATE].trim());
            time = LocalTime.parse(taskInFile[EVENT_TIME].trim());
            item = new Lecture(taskInFile[EVENT_MODULE_CODE], date, time, taskInFile[EVENT_VENUE]);
        } catch (DateTimeException e) {
            System.out.println("Invalid lecture date time " + taskInFile[EVENT_DATE] + " " + taskInFile[EVENT_TIME]
                    + " for " + taskInFile[EVENT_MODULE_CODE]);
        }
        return item;
    }

    /**
     * Reads activity event to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readActivity(String[] taskInFile) {
        CalendarItem item = null;
        LocalTime time;
        LocalDate date;
        if (taskInFile[EVENT_DATE].equals("") || taskInFile[EVENT_TIME].equals("") || taskInFile[DETAILS].equals("")
                || taskInFile[EVENT_VENUE].equals("")) {
            System.out.println("element in file is lost in act: " + taskInFile[EVENT_MODULE_CODE]);
            return null;
        }
        try {
            date = LocalDate.parse(taskInFile[EVENT_DATE].trim());
            time = LocalTime.parse(taskInFile[EVENT_TIME].trim());
            item = new Activity(taskInFile[DETAILS], date, time, taskInFile[EVENT_VENUE]);
        } catch (DateTimeException e) {
            System.out.println("Invalid activity date time " + taskInFile[EVENT_DATE] + " " + taskInFile[EVENT_TIME]
                    + " for " + taskInFile[DETAILS]);
        }
        return item;
    }

    /**
     * Reads deadline task to app.
     *
     * @param taskInFile the data to read.
     * @return item to add.
     */
    private static CalendarItem readDeadlineTask(String[] taskInFile) {
        CalendarItem item = null;
        LocalDate date;
        try {
            date = LocalDate.parse(taskInFile[TASK_DATE].trim());
            item = new Deadline(taskInFile[TASK_DESCRIPTION], date);
        } catch (DateTimeException e) {
            System.out.println("Invalid deadline date " + taskInFile[TASK_DATE]
                    + " for " + taskInFile[TASK_DESCRIPTION]);
        }
        return item;
    }

    /**
     * Adds an item into the calendar list.
     *
     * @param calendarList the calendar list we want to add our item to.
     * @param item         the item we want to add to the calendar list.
     */
    private static void addItemToCalendarList(CalendarList calendarList, CalendarItem item) {
        if (item instanceof Task) {
            calendarList.addTask((Task) item);
        } else if (item instanceof Event) {
            calendarList.addEvent((Event) item);
        }
    }

    /**
     * Marks a task as important.
     *
     * @param item       the task we need to mark as important.
     * @param taskInFile the data stored in the local file.
     */
    private static void markTaskAsImportant(CalendarItem item, String[] taskInFile) {
        if (item instanceof Task) {
            if (taskInFile[TASK_IMPORTANT].equals("true")) {
                ((Task) item).markAsImportant();
            }
        }
    }

    /**
     * Marks an event as over.
     *
     * @param item       the event we need to mark as over.
     * @param taskInFile the data stored in the local file.
     */
    private static void markEventAsOver(CalendarItem item, String[] taskInFile) {
        if (item instanceof Event) {
            if (taskInFile[EVENT_IS_OVER].equals("true")) {
                ((Event) item).markAsOver();
            }
        }
    }

    /**
     * Marks a task as done.
     *
     * @param item       the task that we need to mark as done.
     * @param taskInFile the data stored in local file.
     */
    private static void markTaskAsDone(CalendarItem item, String[] taskInFile) {
        if (item instanceof Task) {
            if (taskInFile[TASK_IS_DONE].equals("true")) {
                ((Task) item).markAsDone();
            }
        }
    }

    /**
     * Loads the additional information stored in the local file.
     *
     * @param item       the event we need to add the additional information.
     * @param taskInFile the data in the local file.
     * @param num        the total splitting number.
     */
    private static void loadAdditionInformation(CalendarItem item, String[] taskInFile, int num) {
        if (item instanceof Event) {
            int i;
            for (i = 1; i <= num - EVENT_ADDITION_INFO - 1; i++) {
                ((Event) item).setAdditionalInformation(taskInFile[i + EVENT_ADDITION_INFO]);
            }
        }
    }

    /**
     * Checks if the module code in the command is valid.
     *
     * @param taskInFile data in file.
     * @return isValid  True if the module code is valid, FALSE otherwise.
     */
    private static boolean isValid(String[] taskInFile) {
        ModuleChecker moduleChecker = new ModuleChecker();
        String moduleCode = taskInFile[2];
        return moduleChecker.isModuleValid(moduleCode);
    }
}
