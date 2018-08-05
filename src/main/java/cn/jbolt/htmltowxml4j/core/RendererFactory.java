package cn.jbolt.htmltowxml4j.core;

import com.codewaves.codehighlight.core.StyleRenderer;
import com.codewaves.codehighlight.core.StyleRendererFactory;
import com.codewaves.codehighlight.renderer.HtmlRenderer;

public class RendererFactory implements StyleRendererFactory {
   public StyleRenderer create(String languageName) {
      return new HtmlRenderer("hljs-");
   }
}