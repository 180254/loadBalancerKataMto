package edu.iis.mto.serverloadbalancer.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import edu.iis.mto.serverloadbalancer.Server;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private static final String DESCRIPTION_STRING = "a server with load percentage of ";
	private static final double EQUALS_EPSILON = 1e-2d;

	double expectedLoadPercentage;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(final Description description) {
		description.appendText(DESCRIPTION_STRING)
				.appendValue(expectedLoadPercentage);
	}

	@Override
	protected void describeMismatchSafely(final Server server, final Description description) {
		description.appendText(DESCRIPTION_STRING)
				.appendValue(server.getCurrentLoadPercentage());
	}

	@Override
	protected boolean matchesSafely(final Server server) {
		return equalsDouble(expectedLoadPercentage, server.getCurrentLoadPercentage());
	}

	protected boolean equalsDouble(double firstValue, double secondValue) {
		return (Math.abs(firstValue - secondValue) < EQUALS_EPSILON);
	}

}
