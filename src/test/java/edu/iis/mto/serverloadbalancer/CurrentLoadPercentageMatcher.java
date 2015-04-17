package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private final String DESCRIBE_STRING = "a server with load percentage of ";
	private final double EQUALS_EPSILON = 1e-5d;

	double expectedPercentageLoad;

	public CurrentLoadPercentageMatcher(double expectedPercentageLoad) {
		this.expectedPercentageLoad = expectedPercentageLoad;
	}

	public void describeTo(Description description) {
		description.appendText(DESCRIBE_STRING)
				.appendValue(expectedPercentageLoad);
	}

	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText(DESCRIBE_STRING)
				.appendValue(server.getCurrentPercentageLoad());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return equalsDouble(expectedPercentageLoad, server.getCurrentPercentageLoad());
	}

	protected boolean equalsDouble(double firstValue, double secondValue) {
		return (Math.abs(firstValue - secondValue) < EQUALS_EPSILON);
	}

}
