package duke;

import duke.command.Command;
import duke.command.CommandList;
import duke.command.CommandMark;
import duke.command.CommandDelete;
import duke.command.CommandAdd;
import duke.command.CommandExit;
import java.time.LocalDateTime;

public class Parser {
    public static Command parseCommand(String fullCommand) throws DukeException {
        String[] commandWords = fullCommand.split(" ");
        switch (commandWords[0]) {
        case "list":
            return new CommandList();
        case "done":
            try {
                return new CommandMark(Integer.parseInt(commandWords[1]));
            } catch (Exception e) {
                throw new DukeException("Invalid task number.");
            }
        case "delete":
            try {
                return new CommandDelete(Integer.parseInt(commandWords[1]));
            } catch (Exception e) {
                throw new DukeException("Invalid task number.");
            }
        case "todo":
        case "deadline":
        case "event":
            try {
                String taskType = fullCommand.split(" ")[0];
                String taskDescription = fullCommand.split(" ", 2)[1];
                return new CommandAdd(taskType, taskDescription);
            } catch (Exception e) {
                throw new DukeException("To add tasks, please use the format: type description\n" +
                        "e.g. todo read book"
                );
            }
        case "bye":
            return new CommandExit();
        default:
            throw new DukeException("I'm sorry, I don't know what '" + commandWords[0] + "' means :(");
        }
    }

    public static LocalDateTime parseDateTimeString(String dateTime) throws DukeException {
        // ensure that dateTime string is in the format: "12/02/2019, 6:05pm"
        try {
            // get day of month, month, and year
            String[] dateArray = dateTime.split(", ")[0].split("/");
            int dayOfMonth = Integer.parseInt(dateArray[0]);
            int month = Integer.parseInt(dateArray[1]);
            int year = Integer.parseInt(dateArray[2]);

            // get minute and hour
            String time = dateTime.split(", ")[1];
            int minute = Integer.parseInt(time.split(":")[1].substring(0, 2));
            int hour = Integer.parseInt(time.split(":")[0]);
            if (hour <= 0) {
                throw new DukeException("Hour cannot be less than or equal to 0");
            }
            boolean isPastNoon = time.substring(time.length() - 2).equalsIgnoreCase("pm");
            if (isPastNoon && hour != 12) {
                // convert to 24 hour format (except for 12pm)
                hour += 12;
            } else if (!isPastNoon && hour == 12) {
                // edge case: 12am to 12:59am
                hour = 0;
            }

            return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        } catch (Exception e) {
            throw new DukeException("DateTime \"" + dateTime + "\" format incorrect");
        }
    }
}