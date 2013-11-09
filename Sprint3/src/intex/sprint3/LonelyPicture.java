package intex.sprint3;



public class LonelyPicture{

	private String GUID;
	private String caption;
	private boolean selected;
	/**
	 * @return the gUID
	 */
	public String getGUID() {
		return GUID;
	}
	/**
	 * @param gUID the gUID to set
	 */
	public void setGUID(String GUID) {
		this.GUID = GUID;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString(){
		return this.caption;
	
	}
	
}
