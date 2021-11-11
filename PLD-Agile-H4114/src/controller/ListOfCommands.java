package controller;

import java.util.LinkedList;

public class ListOfCommands {
    private final LinkedList<Command> LIST;
    private int currentIndex;

    public ListOfCommands() {
        currentIndex = -1;
        LIST = new LinkedList<>();
    }

    /**
     * Add command c to this
     *
     * @param c the command to add
     */
    public void add(Command c) throws Exception {
        int i = currentIndex + 1;
        while (i < LIST.size()) {
            LIST.remove(i);
        }
        currentIndex++;
        LIST.add(currentIndex, c);

        c.doCommand();
    }

    /**
     * Temporary remove the last added command (this command may be reinserted again with redo)
     */
    public void undo() {
        if (currentIndex >= 0) {
            Command cde = LIST.get(currentIndex);
            currentIndex--;
            cde.undoCommand();
        }
    }

    /**
     * Permanently remove the last added command (this command cannot be reinserted again with redo)
     */
    public void cancel() {
        if (currentIndex >= 0) {
            Command cde = LIST.get(currentIndex);
            LIST.remove(currentIndex);
            currentIndex--;
            cde.undoCommand();
        }
    }

    /**
     * Reinsert the last command removed by undo
     */
    public void redo() {
        if (currentIndex < LIST.size() - 1) {
            currentIndex++;
            Command cde = LIST.get(currentIndex);
            try {
                cde.doCommand();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Permanently remove all commands from the list
     */
    public void reset() {
        currentIndex = -1;
        LIST.clear();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public LinkedList<Command> getList() {
        return LIST;
    }
}
