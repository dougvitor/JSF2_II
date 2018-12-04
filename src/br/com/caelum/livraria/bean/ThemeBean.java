package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ThemeBean {
	
	private String theme = "omega";

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String[] getThemes() {
		return new String[] { "afterdark", "afternoon", "afterwork", "aristo",
	            "black-tie", "blitzer", "bluesky", "bootstrap", "casablanca",
	            "cupertino", "cruze", "dark-hive", "delta", "dot-luv",
	            "eggplant", "excite-bike", "flick", "glass-x", "home",
	            "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "omega",
	            "overcast", "pepper-grinder", "redmond", "rocket", "sam",
	            "smoothness", "south-street", "start", "sunny", "swanky-purse",
	            "trontastic", "ui-darkness", "ui-lightness", "vader" };

	}
}
