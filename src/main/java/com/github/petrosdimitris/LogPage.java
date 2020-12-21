package com.github.petrosdimitris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.github.petrosdimitris.domain.Log;
import com.github.petrosdimitris.manager.LogManager;

public class LogPage extends WebPage {
	@SpringBean
	private LogManager manager;

	public LogPage() {
		List<IColumn<Log, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<>(Model.of("id"), "id"));
		columns.add(new PropertyColumn<>(Model.of("date"), "date"));
		columns.add(new PropertyColumn<>(Model.of("logger"), "logger"));
		columns.add(new PropertyColumn<>(Model.of("level"), "level"));
		columns.add(new PropertyColumn<>(Model.of("exception"), "exception"));
		columns.add(new PropertyColumn<>(Model.of("message"), "message"));

		SortableDataProvider<Log, String> provider = new SortableDataProvider<>() {

			@Override
			public Iterator<? extends Log> iterator(long first, long count) {
				return manager.search(first, count).iterator();
			}

			@Override
			public long size() {
				return manager.searchCount();
			}

			@Override
			public IModel<Log> model(Log object) {
				return Model.of(object);
			}

		};

		add(new DefaultDataTable<>("table", columns, provider, 25));

	}
}
