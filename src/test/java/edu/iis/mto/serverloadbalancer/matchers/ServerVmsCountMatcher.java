package edu.iis.mto.serverloadbalancer.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import edu.iis.mto.serverloadbalancer.Server;

public class ServerVmsCountMatcher extends TypeSafeMatcher<Server> {

	int expectedVmsCount;
	
	public ServerVmsCountMatcher(int expectedVmsCount) {
		this.expectedVmsCount = expectedVmsCount;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("server with vms count of ").appendValue(expectedVmsCount);
	}
	
	

	@Override
	protected void describeMismatchSafely(Server item, Description mismatchDescription) {
		mismatchDescription.appendText("server with vms count of ").appendValue(item.getVmsCount());
	}

	@Override
	protected boolean matchesSafely(Server item) {
		return item.getVmsCount() == expectedVmsCount;
	}

}
