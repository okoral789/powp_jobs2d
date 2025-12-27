package edu.kis.powp.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import edu.kis.powp.command.ComplexCommand;
import edu.kis.powp.command.DriverCommand;
import edu.kis.powp.command.OperateToCommand;
import edu.kis.powp.command.SetPositionCommand;
import edu.kis.powp.jobs2d.Job2dDriver;

public final class CrossFactory {

    private CrossFactory() {
    }

    public static DriverCommand create(Supplier<Job2dDriver> driverSupplier, int centerX, int centerY, int size) {
        int half = size / 2;
        List<DriverCommand> commands = new ArrayList<>();

        commands.add(new SetPositionCommand(driverSupplier, centerX - half, centerY));
        commands.add(new OperateToCommand(driverSupplier, centerX + half, centerY));
        commands.add(new SetPositionCommand(driverSupplier, centerX, centerY - half));
        commands.add(new OperateToCommand(driverSupplier, centerX, centerY + half));

        return new ComplexCommand(commands);
    }
}
