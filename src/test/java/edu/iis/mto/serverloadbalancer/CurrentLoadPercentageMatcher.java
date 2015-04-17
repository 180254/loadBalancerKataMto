package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {

	double expectedPercentageLoad;

	public CurrentLoadPercentageMatcher(double expectedPercentageLoad) {
		this.expectedPercentageLoad = expectedPercentageLoad;
	}

	public void describeTo(Description description) {
		description.appendText("a server with load percentage of ")
				.appendValue(expectedPercentageLoad);
	}

	@Override
	protected void describeMismatchSafely(Server server, Description description) {
		description.appendText("a server with load percentage of ")
				.appendValue(server.currentPercentageLoad);
	}

	@Override
	protected boolean matchesSafely(Server item) {
		return Math.abs(expectedPercentageLoad - item.currentPercentageLoad) < 0.001;
	}

}
