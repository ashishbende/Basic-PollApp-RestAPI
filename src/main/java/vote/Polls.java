package vote;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

public class Polls {
	
	@JsonView(Display.WithoutResult.class)
	private  String id;
	
	@JsonIgnore
	private  String moderator_id;
	
	@JsonView(Display.WithoutResult.class)
	@NotNull
	private  String question;
	
	@JsonView(Display.WithoutResult.class)
	@NotNull
	private  String started_at;
	
	@JsonView(Display.WithoutResult.class)
	@NotNull
	private  String expired_at;
	
	@JsonView(Display.WithoutResult.class)
	@NotNull
	private  String[] choice;	
	
	private  Integer[] results;
	
	
	
	public Polls() {
	//	super();
	}

	public Polls(String id,String moderator_id, String question , String started_at, String expired_at, String[] choice){
		//super();
		this.id = id;
		this.moderator_id = moderator_id;
		this.question = question;
		this.started_at = started_at;
		this.expired_at = expired_at;
		this.choice = choice;
		this.results = new Integer[]{0,0};
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getModerator_id() {
		return moderator_id;
	}

	public void setModerator_id(String moderator_id) {
		this.moderator_id = moderator_id;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String stared_at) {
		this.started_at = stared_at;
	}
	public String getExpired_at() {
		return expired_at;
	}
	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}
	public String[] getChoice() {
		return choice;
	}
	public void setChoice(String[] choice) {
		this.choice = choice;
	}

	public Integer[] getResults() {
		return results;
	}

	public void setResults(Integer[] results) {
		this.results = results;
	}


}
