package edu.kis.powp.jobs2d.drivers.adapter;

import java.util.function.Supplier;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.Job2dDriver;

public class LineDrawerAdapter implements Job2dDriver {

    private int startX = 0;
    private int startY = 0;

    private final DrawPanelController panel;
    private final Supplier<ILine> lineSupplier;

    public LineDrawerAdapter(DrawPanelController panel, Supplier<ILine> lineSupplier) {
        this.panel = panel;
        this.lineSupplier = lineSupplier;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        ILine line = lineSupplier.get();

        line.setStartCoordinates(startX, startY);
        line.setEndCoordinates(x, y);

        panel.drawLine(line);

        startX = x;
        startY = y;
    }

    @Override
    public String toString() {
        return "Line Drawer (configurable)";
    }
}
