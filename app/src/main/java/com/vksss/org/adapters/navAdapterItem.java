package com.vksss.org.adapters;

public class navAdapterItem {
	
	private String title;
    private String tag;
    private int icon;
    private String count = "0";
    // boolean to set visibility of the counter
    private boolean isCounterVisible = false;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getCount() {
		return count;
	}
	public boolean getCounterVisible() {
		return this.isCounterVisible ;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public boolean isCounterVisible() {
		return isCounterVisible;
	}
	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
	public navAdapterItem(String title, String tag, int icon){
        this.title = title;
        this.tag = tag;
        this.icon = icon;
    }
}
