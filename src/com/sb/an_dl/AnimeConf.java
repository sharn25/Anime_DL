package com.sb.an_dl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class AnimeConf {
	private File configFile;
	private JSONObject animeObj;
	private JSONObject epObj;
	
	public JSONObject getAnimeObj() {
		if(animeObj==null){
			animeObj = new JSONObject();
		}
		return animeObj;
	}

	public JSONObject getEpObj() {
		if(epObj==null){
			epObj = new JSONObject();
		}
		return epObj;
	}

	

	public AnimeConf(String animePath){
		configFile = new File(animePath, StaticResource.ANIME_CONF);
	}
	
	public boolean isConfigExist(){
		return configFile.exists();
	}
	
	public void loadJSON(Anime anime) throws JSONException{
		if(isConfigExist()){
			 String text = StaticResource.fileToString(configFile);
			 if(!text.equals("")){
				 animeObj = new JSONObject(text);
				 epObj = animeObj.getJSONObject("eps");
			 }
		}else{
			createConfigFile(anime);
		}
	}
	
	public void createConfigFile(Anime anime) throws JSONException{
		animeObj = getAnimeObj();
		animeObj.put("name", anime.getName());
		animeObj.put("epnum", anime.getepnum());
		animeObj.put("details", anime.getdetails());
		animeObj.put("url", anime.geturl());
		animeObj.put("icon", configFile.getParent() + File.separator + "animeIcon.png");
		epObj = getEpObj();
		
		//save Image
		StaticResource.saveAnimeIcon(configFile.getParent(),anime.geticon());
	}
	
	public void addEP(String name, String location) throws JSONException{
		getEpObj().put(name, location);
		getAnimeObj().put("eps", getEpObj());
	}
	
	public void removeEP(String name) throws JSONException{
		getEpObj().remove(name);
		getAnimeObj().put("eps", getEpObj());
	}
	
	public boolean saveAndClose(){
		FileWriter file = null;
		try {
			file = new FileWriter(configFile.getAbsolutePath());
			file.write(animeObj.toString());
			file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return true;
	}
	
}
