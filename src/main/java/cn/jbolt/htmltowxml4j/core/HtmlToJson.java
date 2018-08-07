package cn.jbolt.htmltowxml4j.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;

import cn.jbolt.htmltowxml4j.api.Params;

/**
 * html转Json 适配html2wxml
 * 
 * @author 山东-小木 https://my.oschina.net/imhoodoo
 *
 */
public class HtmlToJson {
	private String html;// 待转Html
	private Params params;
	private int idx;// 图片资源idx html2wxml里存在这个值
	private boolean needAbsUrl=false;

	public static HtmlToJson by(String html,Params params) {
		return new HtmlToJson(html,params);
	}
	
	private HtmlToJson(String html,Params params) {
		this.html = html;
		this.params=params;
		this.idx = 0;
		this.needAbsUrl=StrKit.notBlank(params.getBaseUri());
	}
	

	/**
	 * 获取转换后的JSON 数组 有多级子节点
	 * @return
	 */
	public String get() {
		if (StrKit.isBlank(html)) {
			return null;
		}
		Document document = null;
		try {
			if (needAbsUrl) {
				document = Jsoup.parse(html, params.getBaseUri());
			} else {
				document = Jsoup.parse(html);
			}
		} finally {
			if (document == null) {
				return null;
			}
		}
		Elements elements = document.selectFirst("body").children();
		if(elements.isEmpty()){
			return null;
		}
		JSONArray array = new JSONArray();
		JSONObject jsonObject = null;
		for (Element element : elements) {
			boolean needClass=element.tagName().toLowerCase().equals("pre");
			jsonObject = convertElementToJsonObject(element,needClass);
			if (jsonObject != null) {
				array.add(jsonObject);
			}
		}

		return array.toJSONString();
	}
	
	/**
	 * 将一个节点元素转为JsonObject
	 * @param element
	 * @param needClass 
	 * @return
	 */
	private JSONObject convertElementToJsonObject(Element element, boolean needClass) {
		//如果这个元素没有内容 忽略掉
		if (elementIsEmpty(element)) {
			return null;
		}
		String tag = element.tagName().toLowerCase();
		JSONObject eleJsonObj = new JSONObject();
		if(tag.equals("pre")){
			processTagPre(eleJsonObj, element);
		}else{
			// 1、处理主要的tag type attr
			processMain(element, tag, eleJsonObj);
			// 2、处理Style
			processStyle(element, tag, eleJsonObj);
			if(needClass){
				processClass(element, tag, eleJsonObj);
			}
			// 3、处理子节点
			processChildNodes(element, tag, eleJsonObj,needClass);
		}
		
		return eleJsonObj;
	}
	/**
	 * 判断空元素
	 * 条件是 没有文本型内容 不包含图片、视频、音频数据、还没有Style或者class的标签
	 * @param element
	 * @return
	 */
	private boolean elementIsEmpty(Element element) {
		return element.hasText() == false && 
			   element.selectFirst("img") == null&&
			   element.selectFirst("video") == null&&
			   element.selectFirst("audio") == null&&
			   element.hasAttr("style")==false&&
			   element.hasAttr("class")==false;
	}

	/**
	 * 处理主要的tab type attr a和img特殊处理href和src
	 * @param element
	 * @param tag
	 * @param eleJsonObj
	 */
	private void processMain(Element element, String tag, JSONObject eleJsonObj) {
		eleJsonObj.put("tag", tag);
		eleJsonObj.put("type", element.isBlock() ? "block" : "inline");
		if (tag.equals("a")) {
			processTagA(eleJsonObj, element);
		} else if (tag.equals("img")) {
			processTagImg(eleJsonObj, element);
		} else if (tag.equals("video")||tag.equals("audio")) {
			processTagVideoOrAudio(eleJsonObj,tag, element);
		} 
	}
	/**
	 * 处理Video标签或者audio标签
	 * @param node
	 * @param element
	 */
	private void processTagVideoOrAudio(JSONObject node,String tag, Element element) {
		JSONObject attr = new JSONObject();
		String src=element.attr("src");
		if(needAbsUrl){
			if(src.startsWith("http")){
				attr.put("src",src );
			}else{
				attr.put("src", element.absUrl("src"));
			}
		}else{
			attr.put("src",src );
		}
		
		if(element.hasAttr("controls")){
			attr.put("controls","controls");
		}
		if(element.hasAttr("autoplay")){
			attr.put("autoplay","autoplay");
		}
		if(element.hasAttr("loop")){
			attr.put("loop","loop");
		}
		if(element.hasAttr("muted")&&tag.equals("video")){
			attr.put("muted","muted");
		}
		if(tag.equals("audio")){
			if(element.hasAttr("name")){
				attr.put("name", element.attr("name"));
			}
			if(element.hasAttr("author")){
				attr.put("author", element.attr("author"));
			}
		}
		
		if(element.hasAttr("poster")){
			String poster=element.attr("poster");
			if(needAbsUrl){
				if(src.startsWith("http")){
					attr.put("poster",poster);
				}else{
					attr.put("poster", element.absUrl("poster"));
				}
			}else{
				attr.put("poster",poster);
			}
		}
		
		node.put("attr", attr);
	}
	

	/**
	 * 处理代码高亮
	 * @param eleJsonObj
	 * @param element
	 */
	private void processTagPre(JSONObject node, Element element) {
		node.put("tag", "pre");
		node.put("type", element.isBlock() ? "block" : "inline");
		JSONObject attr = new JSONObject();
		attr.put("class", "hljs");
		node.put("attr", attr);
		
		/*final Highlighter highlighter = new Highlighter(new RendererFactory());
		System.out.println(element.text());
		final Highlighter.HighlightResult result = highlighter.highlightAuto(element.text(), null);
		final CharSequence styledCode = result.getResult();
		System.out.println("=====================styledCode======================");
		element.html(styledCode.toString());
		System.out.println(element.outerHtml());*/
		//暂时没调整好highlighter 等后续优化
		String code=element.text();
		element.html(code);
		// 处理Class
		processClass(element, "pre", node);
		processChildNodes(element, "pre", node,true);
	}

	/**
	 * 处理style属性
	 * @param element
	 * @param tag
	 * @param eleJsonObj
	 */
	private void processStyle(Element element, String tag, JSONObject eleJsonObj) {
		if (element.hasAttr("style")) {
			String style = element.attr("style");
			if (StrKit.notBlank(style)) {
				JSONObject attr = eleJsonObj.getJSONObject("attr");
				if (attr == null) {
					attr = new JSONObject();
					eleJsonObj.put("attr", attr);
				}
				attr.put("style", style);
			}
		}
	}
	/**
	 * 处理class属性
	 * @param element
	 * @param tag
	 * @param eleJsonObj
	 */
	private void processClass(Element element, String tag, JSONObject eleJsonObj) {
		if (element.hasAttr("class")) {
			String style = element.attr("class");
			if (StrKit.notBlank(style)) {
				JSONObject attr = eleJsonObj.getJSONObject("class");
				if (attr == null) {
					attr = new JSONObject();
					eleJsonObj.put("attr", attr);
				}
				attr.put("class", style);
			}
		}
	}
	/**
	 * 处理子节点
	 * @param element
	 * @param tag
	 * @param eleJsonObj
	 * @param needClass 
	 */
	private void processChildNodes(Element element, String tag, JSONObject eleJsonObj, boolean needClass) {
		if (tag.toLowerCase().equals("img")) {
			return;
		}
		Elements sonElements = element.children();
		JSONArray nodes = new JSONArray();
		if (sonElements.isEmpty()==false) {
			JSONObject soneleJsonObj = null;
			for (Element son : sonElements) {
				soneleJsonObj = convertElementToJsonObject(son,needClass);
				if (soneleJsonObj != null) {
					nodes.add(soneleJsonObj);
				}
				son.remove();
			}
		}
		if (element.hasText()) {
			JSONObject textObj = new JSONObject();
			textObj.put("tag", "#text");
			textObj.put("text", element.text());
			nodes.add(textObj);
		}
		eleJsonObj.put("nodes", nodes);
	}

	/**
	 * 处理超链接
	 * 
	 * @param jsonObject
	 * @param element
	 */
	private void processTagA(JSONObject node, Element element) {
		JSONObject attr = new JSONObject();
		String href=element.attr("href");
		if(needAbsUrl){
			if(href.startsWith("http")){
				attr.put("href",href );
			}else{
				attr.put("href", element.absUrl("href"));
			}
		}else{
			attr.put("href",href );
		}
		node.put("attr", attr);
	}

	/**
	 * 处理图片
	 * 
	 * @param jsonObject
	 * @param element
	 */
	private void processTagImg(JSONObject node, Element element) {
		JSONObject attr = new JSONObject();
		String href=element.attr("src");
		if(needAbsUrl){
			if(href.startsWith("http")){
				attr.put("src",href );
			}else{
				attr.put("src", element.absUrl("src"));
			}
		}else{
			attr.put("src",href );
		}
		node.put("attr", attr);
		node.put("idx", idx);
		idx++;
	}

}
