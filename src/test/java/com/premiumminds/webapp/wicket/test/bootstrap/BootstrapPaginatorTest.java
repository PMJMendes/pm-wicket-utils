package com.premiumminds.webapp.wicket.test.bootstrap;

import static org.junit.Assert.*;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.premiumminds.webapp.wicket.bootstrap.BootstrapPaginator;

public class BootstrapPaginatorTest {
	@Test
	public void testRenderComponent(){
		WicketTester tester = createTester();
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
			}
		};
		paginator.setTotalPages(10);
		tester.startComponentInPage(paginator);
		tester.assertDisabled("paginator:first:link");
		tester.assertDisabled("paginator:previous:link");
		tester.assertEnabled("paginator:next:link");
		tester.assertEnabled("paginator:last:link");
	}

	@Test
	public void testLastButton(){
		WicketTester tester = createTester();
		final Boxed<Integer> pageBox = new Boxed<Integer>();
		
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
				pageBox.value=page.getObject();
			}
		};
		paginator.setTotalPages(10);
		tester.startComponentInPage(paginator);
		tester.clickLink("paginator:last:link");
		assertEquals(9, (int) pageBox.value);
		tester.assertDisabled("paginator:last:link");
		tester.assertDisabled("paginator:next:link");
		tester.assertEnabled("paginator:previous:link");
		tester.assertEnabled("paginator:first:link");
	}

	@Test
	public void testNextButton(){
		WicketTester tester = createTester();
		final Boxed<Integer> pageBox = new Boxed<Integer>();
		
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
				pageBox.value=page.getObject();
			}
		};
		paginator.setTotalPages(10);
		tester.startComponentInPage(paginator);
		tester.clickLink("paginator:next:link");
		assertEquals(1, (int) pageBox.value);
		tester.assertEnabled("paginator:last:link");
		tester.assertEnabled("paginator:next:link");
		tester.assertEnabled("paginator:previous:link");
		tester.assertEnabled("paginator:first:link");
	}
	
	@Test
	public void testPreviousButton(){
		WicketTester tester = createTester();
		final Boxed<Integer> pageBox = new Boxed<Integer>();
		
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
				pageBox.value=page.getObject();
			}
		};
		paginator.setTotalPages(10);
		paginator.setModelObject(2);
		tester.startComponentInPage(paginator);
		tester.clickLink("paginator:previous:link");
		assertEquals(1, (int) pageBox.value);
		tester.assertEnabled("paginator:last:link");
		tester.assertEnabled("paginator:next:link");
		tester.assertEnabled("paginator:previous:link");
		tester.assertEnabled("paginator:first:link");
	}

	@Test
	public void testFirstButton(){
		WicketTester tester = createTester();
		final Boxed<Integer> pageBox = new Boxed<Integer>();
		
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
				pageBox.value=page.getObject();
			}
		};
		paginator.setTotalPages(10);
		paginator.setModelObject(2);
		tester.startComponentInPage(paginator);
		tester.clickLink("paginator:first:link");
		assertEquals(0, (int) pageBox.value);
		tester.assertEnabled("paginator:last:link");
		tester.assertEnabled("paginator:next:link");
		tester.assertDisabled("paginator:previous:link");
		tester.assertDisabled("paginator:first:link");
	}
	
	@Test
	public void testNumberButton(){
		WicketTester tester = createTester();
		final Boxed<Integer> pageBox = new Boxed<Integer>();
		
		BootstrapPaginator paginator = new BootstrapPaginator("paginator") {
			private static final long serialVersionUID = -4486050808642574868L;

			@Override
			public void onPageChange(AjaxRequestTarget target, IModel<Integer> page) {
				pageBox.value=page.getObject();
			}
		};
		paginator.setTotalPages(10);
		tester.startComponentInPage(paginator);
		tester.clickLink("paginator:page:4:link");
		assertEquals(4, (int) pageBox.value);
		tester.assertEnabled("paginator:last:link");
		tester.assertEnabled("paginator:next:link");
		tester.assertEnabled("paginator:previous:link");
		tester.assertEnabled("paginator:first:link");
		tester.assertLabel("paginator:page:2:link:label", "5");
	}
	
	private WicketTester createTester(){
		WicketTester tester = new WicketTester(new WebApplication() {
			
			@Override
			public Class<? extends Page> getHomePage() {
				return null;
			}
		}){
			@Override
			protected String createPageMarkup(String componentId) {
				return "<div class=\"pagination pagination-small pagination-right\" wicket:id=\"paginator\">"+
						"</div>";
			}
		};
		
		return tester;
	}
	
	private static class Boxed<T> {
		public T value;
	}
}