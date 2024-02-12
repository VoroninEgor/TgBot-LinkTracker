package edu.java.bot.utill.command;


public class CommandAnalyzer {
    public static Command typeOfCommand(String text) {
        if (text == null || text.isEmpty()) {
            return Command.UNKNOWN;
        }

        for (Command command : Command.values()) {
            if (text.startsWith(command.getPrefix())) {
                return command;
            }
        }

        return Command.UNKNOWN;
    }

}
