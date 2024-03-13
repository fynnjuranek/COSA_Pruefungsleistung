package de.leuphana.cosa.componentservicebus;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.componentservicebus.behaviour.ComponentServiceBus;
import de.leuphana.cosa.documentsystem.behaviour.DocumentSystem;
import de.leuphana.cosa.documentsystem.behaviour.service.command.DocumentSystemCommandService;
import de.leuphana.cosa.messagingsystem.behaviour.MessagingSystem;
import de.leuphana.cosa.messagingsystem.behaviour.service.command.MessagingSystemCommandService;
import de.leuphana.cosa.pricingsystem.behaviour.PricingSystem;
import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.printingsystem.behaviour.PrintingSystem;
import de.leuphana.cosa.printingsystem.behaviour.service.command.PrintingCommandService;
import de.leuphana.cosa.routesystem.behaviour.RouteSystem;
import de.leuphana.cosa.routesystem.behaviour.service.command.RouteSystemCommandService;
import de.leuphana.cosa.ticketautomaton.behaviour.TicketAutomaton;
import de.leuphana.cosa.ticketautomaton.behaviour.service.command.TicketAutomatonCommandService;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComponentServiceBusTest {

	private static DocumentSystemCommandService documentService;
	
	@BeforeAll
	public static void setup() {
		
	}
	
	@AfterAll
	public static void tearDown() {
		
	}
	
	@Test
	@Order(1)
	void canComponentServiceBusBeAccessed() {
		EventHandler componentServiceBus = getService(EventHandler.class, ComponentServiceBus.class);
		Assertions.assertNotNull(componentServiceBus);
	}
	
	@Test
	@Order(2)
	void canDocumentServiceBeAccessed() {
		documentService = getService(DocumentSystemCommandService.class, DocumentSystem.class);
		Assertions.assertNotNull(documentService);
	}
	
	@Test
	@Order(3)
	void canMessagingServiceBeAccessed() {
		MessagingSystemCommandService messagingService = getService(MessagingSystemCommandService.class, MessagingSystem.class);
		Assertions.assertNotNull(messagingService);
	}
	
	@Test
	@Order(4)
	void canPricingServiceBeAccessed() {
		PricingSystemCommandService pricingService = getService(PricingSystemCommandService.class, PricingSystem.class);
		Assertions.assertNotNull(pricingService);
	}
	
	@Test
	@Order(5)
	void canPrintingServiceBeAccessed() {
		PrintingCommandService printingService = getService(PrintingCommandService.class, PrintingSystem.class);
		Assertions.assertNotNull(printingService);
	}
	
	@Test
	@Order(6)
	void canRouteServiceBeAccessed() {
		RouteSystemCommandService routeService = getService(RouteSystemCommandService.class, RouteSystem.class);
		Assertions.assertNotNull(routeService);
	}
	
	@Test
	@Order(7)
	void canTicketServiceBeAccessed() {
		TicketAutomatonCommandService ticketService = getService(TicketAutomatonCommandService.class, TicketAutomaton.class);
		Assertions.assertNotNull(ticketService);
	}

	// TODO: Test the event-handling function of componentServiceBus
	
	// helper-function
		static <T, Y> T getService(Class<T> clazz, Class<Y> bundleClass) {
	        Bundle bundle = FrameworkUtil.getBundle(bundleClass);
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
