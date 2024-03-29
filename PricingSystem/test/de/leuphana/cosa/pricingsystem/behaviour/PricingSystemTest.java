package de.leuphana.cosa.pricingsystem.behaviour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import de.leuphana.cosa.pricingsystem.behaviour.service.command.PricingSystemCommandService;
import de.leuphana.cosa.pricingsystem.structure.Chargeable;
import de.leuphana.cosa.pricingsystem.structure.Price;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PricingSystemTest {

	private static PricingSystemCommandService pricingService;
	
	private static Chargeable chargeable;
	
	@BeforeAll
	static void setup() {
		chargeable = new Chargeable() {
			
			@Override
			public double getRouteDistance() {
				// TODO Auto-generated method stub
				return 662.24;
			}
			
			@Override
			public PriceGroup getPriceGroup() {
				// TODO Auto-generated method stub
				return PriceGroup.BARGAIN_TARIFF;
			}
		};
	}
	
	@AfterAll
	static void tearDown() {
		chargeable = null;
		pricingService = null;
	}
	
	@Test
	@Order(1)
	void canPricingServiceBeAccessed() {
		pricingService = getService(PricingSystemCommandService.class);
		Assertions.assertNotNull(pricingService);
	}
	
	@Test
	@Order(2)
	void canPriceBeCalculated() {
		Price price = pricingService.calculatePrice(chargeable);
		System.out.println("Calculated price " + price.getAmount() + " for pricegroup " + price.getPriceGroup() + " and distance " + chargeable.getRouteDistance());
		Assertions.assertNotNull(price);
	}

	
	// helper-function to retrieve Service!
			static <T> T getService(Class<T> clazz) {
		        Bundle bundle = FrameworkUtil.getBundle(PricingSystem.class);
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
