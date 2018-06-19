package com.hc.hclvzh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hc.hclvzh.msg.WechatProcess;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GreetingController {
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
		GreetingController methodOn = methodOn(GreetingController.class);
		// GreetingController methodOn =
		// DummyInvocationUtils.methodOn(GreetingController.class);
		Greeting greeting2 = methodOn.greeting(name);
		ControllerLinkBuilder linkTo = linkTo(greeting2);
		Link withSelfRel = linkTo.withSelfRel();
		greeting.add(withSelfRel);

		return greeting;
	}

	@RequestMapping("/show")
	public Greeting show() {
		RestTemplate template = new RestTemplate();
		Greeting greeting = template.getForObject("http://localhost:8080/greeting?name=323", Greeting.class);
		System.err.println(greeting);
		return greeting;
	}

	@RequestMapping("weixin")
	public void weixin(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");// POST提交有效
		response.setContentType("text/html;charset=UTF-8");

		boolean isGet = request.getMethod().toLowerCase().equals("get");

		PrintWriter print = null;
		String result = "";
		// if (isGet) {

		// 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (echostr != null && echostr.length() > 0) {

			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");

			System.out.println("signature:" + signature);

			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				result = echostr;
			}
		} else {

			/** 读取接收到的xml消息 */
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			// 次即为接收到微信端发送过来的xml数据
			String xml = sb.toString();

			System.out.println("xml:" + xml);

			// 正常的微信处理流程
			result = new WechatProcess().processWechatMag(xml);
		}

		// 向请求者返回数据
		try {
			print = response.getWriter();
			print.write(result);
			print.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (print != null) {
				print.close();
			}
		}
		
//		system.out  
//        System.out.println("参数调试！输出自System.out.println！");  
//        System.out.println("参数调试！输出自System.out.println！");  
//        System.out.println("参数调试！输出自System.out.println！");  
//          
//        //log.info  
//        Logger.info("调试参数！！！输出自log.info！！！");  
//        log.info("调试参数！！！输出自log.info！！！");  
//        log.info("调试参数！！！输出自log.info！！！");  
//          
//        //log.error  
//        log.error("调试参数！！！输出自log.error！！！");  
//        log.error("调试参数！！！输出自log.error！！！");  
//        log.error("调试参数！！！输出自log.error！！！");  
//          
//        //log.warn  
//        log.warn("调试参数！！！输出自log.warn！！！");  
//        log.warn("调试参数！！！输出自log.warn！！！");  
//        log.warn("调试参数！！！输出自log.warn！！！");  
//          
//        //log.debug  
//        log.debug("姓名：" + report_name + "，输出自log.debug！！！");  
//        log.debug("昵称：" + column1 + "，输出自log.debug！！！");  
//        log.debug("邮箱：" + column2 + "，输出自log.debug！！！");  
//        log.debug("联系方式：" + column3 + "，输出自log.debug！！！");  
//        
		// }
	}
	//
	// private String access_token_url =
	// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//
	// /**
	// * 获取access_token
	// *
	// * @param appid
	// * 凭证
	// * @param appsecret
	// * 密钥
	// * @return
	// */
	// public AccessToken getAccessToken(String appid, String appsecret) {
	// AccessToken accessToken = null;
	// String requestUrl = access_token_url.replace("APPID",
	// appid).replace("APPSECRET", appsecret);
	// JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
	// if (null != jsonObject) {
	// try {
	// accessToken = new AccessToken();
	// accessToken.setAccess_token(jsonObject.getString("access_token"));
	// accessToken.setExpires_in(jsonObject.getInt("expires_in"));
	// } catch (JSONException e) {
	// accessToken = null;
	// // 获取token失败
	// log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
	// jsonObject.getString("errmsg"));
	// }
	// }
	// return accessToken;
	// }

}