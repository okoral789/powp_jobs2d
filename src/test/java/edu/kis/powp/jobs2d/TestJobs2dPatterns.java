package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.powp.command.DriverCommand;
import edu.kis.powp.command.factory.CircleFactory;
import edu.kis.powp.command.factory.CrossFactory;
import edu.kis.powp.command.factory.RectangleFactory;
import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.magicpresets.FiguresJoe;

public class TestJobs2dPatterns {

	private static final Logger logger =
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static void setupPresetTests(Application application) {

		Supplier<Job2dDriver> currentDriver = () -> DriverFeature.getDriverManager().getCurrentDriver();

		// Figure Joe 1 (WYMAGANE)
		application.addTest(
				"Figure Joe 1",
				new SelectTestFigureOptionListener(
						DriverFeature.getDriverManager()
				)
		);

		application.addTest(
				"Figure Joe 2",
				(ActionEvent e) -> FiguresJoe.figureScript2(
						DriverFeature.getDriverManager().getCurrentDriver()
				)
		);

		application.addTest(
				"Command rectangle",
				(ActionEvent e) -> {
					DriverCommand rectangle = RectangleFactory.create(currentDriver, -120, -80, 240, 160);
					rectangle.execute();
				}
		);

		application.addTest(
				"Command circle",
				(ActionEvent e) -> {
					DriverCommand circle = CircleFactory.create(currentDriver, 0, 0, 120);
					circle.execute();
				}
		);

		application.addTest(
				"Command cross",
				(ActionEvent e) -> {
					DriverCommand cross = CrossFactory.create(currentDriver, 0, 0, 140);
					cross.execute();
				}
		);
	}

	/**
	 * === DRIVERY (JAK rysować) ===
	 */
	private static void setupDrivers(Application application) {

		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		// Special Line – zadanie 3.3
		Job2dDriver specialLineDriver =
				new LineDrawerAdapter(
						DrawerFeature.getDrawerController(),
						() -> LineFactory.getSpecialLine()
				);
		DriverFeature.addDriver("Special Line Simulator", specialLineDriver);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * === WIDOCZNOŚĆ DRAWERA (JEDNO OKNO) ===
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {

		DefaultDrawerFrame drawerWindow =
				DefaultDrawerFrame.getDefaultDrawerFrame();

		application.addComponentMenuElementWithCheckBox(
				DrawPanelController.class,
				"Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(drawerWindow),
				true
		);
	}

	/**
	 * === LOGGER ===
	 */
	private static void setupLogger(Application application) {

		application.addComponentMenu(Logger.class, "Logger", 0);

		application.addComponentMenuElement(
				Logger.class,
				"Clear log",
				(ActionEvent e) -> application.flushLoggerOutput()
		);

		application.addComponentMenuElement(
				Logger.class,
				"Fine level",
				(ActionEvent e) -> logger.setLevel(Level.FINE)
		);

		application.addComponentMenuElement(
				Logger.class,
				"Info level",
				(ActionEvent e) -> logger.setLevel(Level.INFO)
		);

		application.addComponentMenuElement(
				Logger.class,
				"Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING)
		);

		application.addComponentMenuElement(
				Logger.class,
				"Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE)
		);

		application.addComponentMenuElement(
				Logger.class,
				"OFF logging",
				(ActionEvent e) -> logger.setLevel(Level.OFF)
		);
	}

	/**
	 * === START APLIKACJI ===
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			Application app = new Application("2d jobs Visio");

			DrawerFeature.setupDrawerPlugin(app);
			setupDefaultDrawerVisibilityManagement(app);

			DriverFeature.setupDriverPlugin(app);
			setupDrivers(app);
			setupPresetTests(app);
			setupLogger(app);

			app.setVisibility(true);
		});
	}
}
