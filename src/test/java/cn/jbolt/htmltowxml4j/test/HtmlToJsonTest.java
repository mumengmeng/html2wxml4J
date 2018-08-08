package cn.jbolt.htmltowxml4j.test;

import org.junit.Test;

import cn.jbolt.htmltowxml4j.api.Params;
import cn.jbolt.htmltowxml4j.core.HtmlToJson;
import junit.framework.TestCase;

public class HtmlToJsonTest extends TestCase {
	
	@Test
	public void test() throws Exception {
			String html="<pre>function a(){"
					+ "\n        alert('a');"
					+ "\n}</pre>";
			
			Params params=new Params();
			params.setType("html");
			String result=HtmlToJson.by(html, params).get();
			assertNotNull(result);
			assertTrue(result.indexOf("pre")!=-1);
			assertTrue(result.indexOf("function")!=-1);
			assertTrue(result.indexOf("alert")!=-1);
			assertTrue(result.indexOf("'a'")!=-1);
			assertTrue(result.indexOf("        ")!=-1);
	}
	
}
