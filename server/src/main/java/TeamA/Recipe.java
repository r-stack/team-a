package TeamA;

public class Recipe {
	private String request_id;
	private String pos_filter;
	private String[][][] word_list;
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getPos_filter() {
		return pos_filter;
	}
	public void setPos_filter(String pos_filter) {
		this.pos_filter = pos_filter;
	}
	public String[][][] getWord_list() {
		return word_list;
	}
	public void setWord_list(String[][][] word_list) {
		this.word_list = word_list;
	}
}
