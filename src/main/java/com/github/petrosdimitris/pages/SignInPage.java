package com.github.petrosdimitris.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

import com.github.petrosdimitris.WicketSession;

@Deprecated
public class SignInPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public SignInPage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		TextField<String> username = new TextField<String>("username", Model.of(""));
		PasswordTextField password = new PasswordTextField("password", Model.of(""));

		StatelessForm form = new StatelessForm("form") {
			@Override
			protected void onSubmit() {
				if (Strings.isEmpty(username.getModelObject()))
					return;

				boolean authResult = WicketSession.get().signIn(username.getModelObject(), password.getModelObject());
				// if authentication succeeds redirect user to the requested page
				if (authResult) {
					continueToOriginalDestination();
					setResponsePage(HomePage.class);
				}
			}
		};

		form.setDefaultModel(new CompoundPropertyModel(this));

		form.add(username);
		form.add(password);

		add(form);
	}
}
