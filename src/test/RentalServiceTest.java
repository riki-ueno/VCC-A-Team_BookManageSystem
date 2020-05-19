package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import app.service.RentalService;

class RentalServiceTest {

	@Test
	void testCall() {
		assertEquals(RentalService.call(1, 1), true);
		assertEquals(RentalService.call(2, 1), false);
		assertEquals(RentalService.call(5, 1), true);
		assertEquals(RentalService.call(5, 2), false);
		assertEquals(RentalService.call(6, 1), true);
		assertEquals(RentalService.call(6, 2), false);
	}

}
