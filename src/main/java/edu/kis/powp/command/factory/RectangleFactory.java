package edu.kis.powp.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import edu.kis.powp.command.ComplexCommand;
import edu.kis.powp.command.DriverCommand;
import edu.kis.powp.command.OperateToCommand;
import edu.kis.powp.command.SetPositionCommand;
import edu.kis.powp.jobs2d.Job2dDriver;

public final class RectangleFactory {

    private RectangleFactory() {
    }

    public static DriverCommand create(Supplier<Job2dDriver> driverSupplier, int startX, int startY, int width, int height) {
        List<DriverCommand> commands = new ArrayList<>();

        commands.add(new SetPositionCommand(driverSupplier, startX, startY));
        commands.add(new OperateToCommand(driverSupplier, startX + width, startY));
        commands.add(new OperateToCommand(driverSupplier, startX + width, startY + height));
        commands.add(new OperateToCommand(driverSupplier, startX, startY + height));
        commands.add(new OperateToCommand(driverSupplier, startX, startY));

        return new ComplexCommand(commands);
    }
}
