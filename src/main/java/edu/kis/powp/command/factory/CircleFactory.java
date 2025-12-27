package edu.kis.powp.command.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import edu.kis.powp.command.ComplexCommand;
import edu.kis.powp.command.DriverCommand;
import edu.kis.powp.command.OperateToCommand;
import edu.kis.powp.command.SetPositionCommand;
import edu.kis.powp.jobs2d.Job2dDriver;

public final class CircleFactory {
    private static final int DEFAULT_SEGMENTS = 24;

    private CircleFactory() {
    }

    public static DriverCommand create(Supplier<Job2dDriver> driverSupplier, int centerX, int centerY, int radius) {
        return create(driverSupplier, centerX, centerY, radius, DEFAULT_SEGMENTS);
    }

    public static DriverCommand create(Supplier<Job2dDriver> driverSupplier, int centerX, int centerY, int radius, int segments) {
        int safeSegments = Math.max(4, segments);
        double angleStep = (2 * Math.PI) / safeSegments;

        List<DriverCommand> commands = new ArrayList<>();
        int startX = centerX + radius;
        int startY = centerY;

        commands.add(new SetPositionCommand(driverSupplier, startX, startY));

        for (int i = 1; i <= safeSegments; i++) {
            double angle = i * angleStep;
            int x = centerX + (int) Math.round(Math.cos(angle) * radius);
            int y = centerY + (int) Math.round(Math.sin(angle) * radius);
            commands.add(new OperateToCommand(driverSupplier, x, y));
        }

        return new ComplexCommand(commands);
    }
}
