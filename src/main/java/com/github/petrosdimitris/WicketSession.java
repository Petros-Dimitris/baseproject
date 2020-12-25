package com.github.petrosdimitris;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.github.petrosdimitris.manager.SignInManager;

public class WicketSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private SignInManager signInManager;

	private Roles roles;

	public WicketSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	public static WicketSession get() {
		return (WicketSession) Session.get();
	}

	@Override
	protected boolean authenticate(String username, String password) {
		roles = signInManager.signIn(username, password);
		return roles != null;
	}

	@Override
	public Roles getRoles() {
		return roles;
	}

}
