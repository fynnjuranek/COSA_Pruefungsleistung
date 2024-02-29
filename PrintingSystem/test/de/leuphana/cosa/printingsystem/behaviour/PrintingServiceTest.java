package de.leuphana.cosa.printingsystem.behaviour;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.printingsystem.structure.PrintOptions;
import de.leuphana.cosa.printingsystem.structure.Printable;
import de.leuphana.cosa.printingsystem.structure.UserAccount;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrintingServiceTest {

	private Printable printable;
	private static PrintingCommandService printingService;
	private PrintOptions printOptions;
	private UserAccount userAccount;

	@BeforeEach
	void setUp() throws Exception {
		//printingService = getService(PrintingCommandService.class);
		printOptions = new PrintOptions();
		userAccount = new UserAccount();
		// anonyme innere Klassen
		printable = new Printable() {

			public String getTitle() {
				return "new document name";
			}

			public String getContent() {
				return "new content";
			}
		};


	}

	@AfterEach
	void tearDown() throws Exception {
		//printingService = null;
		printOptions = null;
		userAccount = null;
		printable = null;
	}

	@Test
	@Order(1)
	void canPrintingServiceBeAccessed() {
		printingService = getService(PrintingCommandService.class);
		Assertions.assertNotNull(printingService);
	}
	
	@Test
	@Order(2)
	void canDocumentBePrintedTest() {
		Assertions.assertNotNull(printingService.printDocument(printable, 0.0, userAccount));
	}

	// helper-function
	static <T> T getService(Class<T> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(PrintingSystem.class);
        if (bundle != null) {
            ServiceTracker<T, T> st =
                new ServiceTracker<T, T>(
                    bundle.getBundleContext(), clazz, null);
            st.open();
            if (st != null) {
                try {
                    // give the runtime some time to startup
                    return st.waitForService(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}