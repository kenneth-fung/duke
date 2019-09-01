package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;

/**
 * A Command to list all tasks in the task list.
 */
public class CommandList extends Command {
    /**
     * Prints the entire TaskList through the UI.
     * @param tasks The TaskList containing the user's added Tasks.
     * @param ui The UI to interact with the user by printing instructions/messages.
     * @param storage Storage to use for loading/saving tasks from/to a file on the hard disk.
     * @return Duke's response to the Command as a String.
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.getTaskList(tasks);
    }
}
