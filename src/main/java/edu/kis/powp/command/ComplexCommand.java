package edu.kis.powp.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComplexCommand implements DriverCommand {
    private final List<DriverCommand> commands;

    public ComplexCommand() {
        this.commands = new ArrayList<>();
    }

    public ComplexCommand(Collection<DriverCommand> commands) {
        this.commands = new ArrayList<>(commands);
    }

    public ComplexCommand addCommand(DriverCommand command) {
        commands.add(command);
        return this;
    }

    @Override
    public void execute() {
        commands.forEach(DriverCommand::execute);
    }
}
