package cn.jbolt.htmltowxml4j.api;

import java.util.Collections;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import cn.jbolt.htmltowxml4j.core.HtmlToJson;
/**
 * htmltowxml接口
 * @author  山东-小木 https://my.oschina.net/imhoodoo
 *
 */
public class IndexController extends Controller {
	/**
	 * 获取转换条件
	 * @return
	 */
	private Params getParams(){
		//类型 默认HMTL
		String type=getPara("type",Params.TYPE_HTML);
		//是否开启pre代码高亮 默认开启
		Boolean highlight=getParaToBoolean("highlight",true);
		//是否开启pre代码行号 默认开启
		Boolean linenums=getParaToBoolean("linenums",true);
		//获取a和img静态资源的根路径URL
		String baseUri=getPara("host",getPara("imghost"));//后面版本兼容更新会修改名称 这里先行适配
		
		//这里因为需要适配html2wxml前端小程序 所以只能这么写
		//拿到html2wxml组建版源码可以自行修改传递参数包装起来传递{"params":params}
		//使用Params params=getBean(Params.class); 就能直接获取了
		Params params=new Params();
		params.setHighlight(highlight);
		params.setLinenums(linenums);
		params.setType(type);
		params.setBaseUri(baseUri);
		return params;
	}
	/**v
	 * 转换接口主入口
	 */
	public void index(){
		//获取需要转化的文本内容
		String text=getPara("text");
		//获取转换条件参数
		Params params=getParams();
		//执行转换 返回结果
//		long startTime=System.currentTimeMillis();
		String resultJson=HtmlToJson.by(text,params).get();
//		long endTime=System.currentTimeMillis();
//		long useTime=endTime-startTime;
//		System.out.println("耗时:"+useTime);
		if(StrKit.isBlank(resultJson)){
			renderJson(Collections.emptyList());
		}else{
			renderJson(resultJson);
		}
	}
}
