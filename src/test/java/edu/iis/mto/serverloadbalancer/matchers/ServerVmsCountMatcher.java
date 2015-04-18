package edu.iis.mto.serverloadbalancer.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import edu.iis.mto.serverloadbalancer.Server;

public class ServerVmsCountMatcher extends TypeSafeMatcher<Server> {

	private static final String DESCRIPTION_STRING = "server with vms count of ";

	int expectedVmsCount;

	public ServerVmsCountMatcher(int expectedVmsCount) {
		this.expectedVmsCount = expectedVmsCount;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText(DESCRIPTION_STRING)
				.appendValue(expectedVmsCount);
	}

	@Override
	protected void describeMismatchSafely(final Server server, Description description) {
		description.appendText(DESCRIPTION_STRING)
				.appendValue(server.getVmsCount());
	}

	@Override
	protected boolean matchesSafely(Server item) {
		return item.getVmsCount() == expectedVmsCount;
	}

}
