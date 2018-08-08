package cn.jbolt.htmltowxml4j.test;

import org.junit.Test;

import cn.jbolt.htmltowxml4j.api.Params;
import cn.jbolt.htmltowxml4j.core.HtmlToJson;
import junit.framework.TestCase;

public class HtmlToJsonTest extends TestCase {
	
	@Test
	public void test() throws Exception {
		String html="<div style=\"sss\" id=\"b\">cccccc<pre class=\"a\"><script src=\"script.js\">scripot</script> <link href=\"c.css\"></link></pre></div>";
			
			Params params=new Params();
			params.setType("html");
			String result=HtmlToJson.by(html, params).get();
			System.out.println(result);
			assertNotNull(result);
			assertTrue(result.indexOf("link")==-1);
			assertTrue(result.indexOf("script")==-1);
			assertTrue(result.indexOf("cccccc")!=-1);
			assertTrue(result.indexOf("sss")!=-1);
			assertTrue(result.indexOf("pre")!=-1);
	}
	
}
