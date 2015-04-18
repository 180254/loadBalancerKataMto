package edu.iis.mto.serverloadbalancer.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import edu.iis.mto.serverloadbalancer.Server;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	private final String DESCRIBE_STRING = "a server with load percentage of ";
	private final double EQUALS_EPSILON = 1e-5d;

	double expectedLoadPercentage;

	public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText(DESCRIBE_STRING)
				.appendValue(expectedLoadPercentage);
	}

	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText(DESCRIBE_STRING)
				.appendValue(server.getCurrentLoadPercentage());
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return equalsDouble(expectedLoadPercentage, server.getCurrentLoadPercentage());
	}

	protected boolean equalsDouble(double firstValue, double secondValue) {
		return (Math.abs(firstValue - secondValue) < EQUALS_EPSILON);
	}

}
