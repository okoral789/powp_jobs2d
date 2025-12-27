package edu.kis.powp.command;

import java.util.function.Supplier;

import edu.kis.powp.jobs2d.Job2dDriver;

public class OperateToCommand implements DriverCommand {
    private final Supplier<Job2dDriver> driverSupplier;
    private final int x;
    private final int y;

    public OperateToCommand(Supplier<Job2dDriver> driverSupplier, int x, int y) {
        this.driverSupplier = driverSupplier;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        driverSupplier.get().operateTo(x, y);
    }
}
