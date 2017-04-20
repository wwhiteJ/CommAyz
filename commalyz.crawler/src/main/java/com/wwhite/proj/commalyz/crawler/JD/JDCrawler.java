package com.wwhite.proj.commalyz.crawler.JD;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wwhite.proj.commalyz.common.utils.FileUtil;
import com.wwhite.proj.commalyz.common.utils.HttpUtil;

public class JDCrawler {
	
	protected String JD_OUTPUT_RAW_DIR = "E:\\projects\\JDJingPinAnalysis\\datas\\raw\\";
	protected String JD_CRAWL_CONFIG_DIR = "E:\\projects\\JDJingPinAnalysis\\datas\\configs\\";
	
	public JDConfig loadConfig(String productId){
		
		String configFilename = JD_CRAWL_CONFIG_DIR + productId + ".config";
		String page = FileUtil.readStringFromFile(configFilename, "utf-8");
		JDConfig config = null;
		if( page == null || page.length() < 1 ){
			config = new JDConfig();
			config.sku = productId;
			config.commentType = 0;
			config.pageIndex = 0;
		} else{
			config = JSON.parseObject(page, JDConfig.class);
		}
		
		return config;
	}
	
	public void writeConfig(JDConfig config){
		
		String configFilename = JD_CRAWL_CONFIG_DIR + config.sku + ".config";
		System.out.println(JSON.toJSONString(config));
		
		FileUtil.stringToFile(JSON.toJSONString(config), configFilename);
	}
	
	public void crawlComments(String productId){
		
		// load config 
		JDConfig config = this.loadConfig(productId);
		// output dir
		String outputDir = JD_OUTPUT_RAW_DIR + productId + "\\";
		FileUtil.makeDir(outputDir);
		
		String urlTemplate = "https://club.jd.com/comment/productPageComments.action?productId=<PRODUCTID>&score=<COMMENTTYPE>&sortType=6&page=<PAGE>&pageSize=10&isShadowSku=0";
		
		Integer page = config.pageIndex;
		Integer commentType = config.commentType;
		Integer maxPage = -1;
		while(true){
			try{
				String url = urlTemplate.replace("<PRODUCTID>", productId).replace("<PAGE>", page.toString()).replace("<COMMENTTYPE>", commentType.toString());
				String str = HttpUtil.sendGet(url, null);
				if( str == null || "".equals(str) ){
					System.out.println("page null, wait 6 mins ...");
					for( int i=0; i<6; ++i ){
						Thread.sleep(60*1000);
						System.out.println(i+" mins passed ..");
					}
					continue;
				}
				Integer commentsNum = JSON.parseObject(str).getJSONArray("comments").size();
				if( maxPage == -1 || maxPage == null ){
					maxPage = JSON.parseObject(str).getInteger("maxPage");
				}
				if( commentsNum == 0 ){
					System.out.println("comments empty, break");
					break;
				}
				FileUtil.stringToFile(str, outputDir + page);
				config.pageIndex = page;
				this.writeConfig(config);
				System.out.println(page + "," + maxPage);
				page ++;
				if( page > maxPage ){
					System.out.println("reached max page, break");
					break;
				}
				Thread.sleep(500);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args){
		//JDCrawler.crawlComments("3304295", "E:\\projects\\JDJingPinAnalysis\\demo\\comments_raw\\3304295\\");
		JDCrawler jdCrawler = new JDCrawler();
		jdCrawler.crawlComments("1283908");
	}
}
