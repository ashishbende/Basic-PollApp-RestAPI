package vote;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

/*
 * @author Ashish Bende
 */

@RestController
@RequestMapping("/api/v1")
public class PollsController {

	
	private final AtomicLong poll_id = new AtomicLong(98345);
	ArrayList<Polls> poll_list = new ArrayList<Polls>();
	
	@JsonView(Display.WithoutResult.class)
	@RequestMapping(value="/moderators/{moderator_id}/polls", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Polls pollCreate(@PathVariable(value="moderator_id") String id,@RequestBody @Valid  Polls polls){
		String moderator_id = id;
		String question = polls.getQuestion();
		String started_at = polls.getStarted_at();
		String expired_at = polls.getExpired_at();
		String[] choice = polls.getChoice();
		Polls poll_object = new Polls(String.valueOf(poll_id.incrementAndGet()),moderator_id,question, started_at, expired_at, choice);
		poll_list.add(poll_object);
		return poll_object;
	}
	
	@JsonView(Display.WithoutResult.class)
	@RequestMapping(value="/polls/{polls_id}", method=RequestMethod.GET)
	public ResponseEntity<Polls> pollViewNR(@PathVariable("polls_id") String id){
		Polls p1=null;
		boolean b=false;
		for(Polls forPolls:poll_list){
			if(forPolls.getId().equals(id)){
				p1=forPolls;
				b=true;break;
			}
		}
		if(b)
			return new ResponseEntity<Polls>(p1,HttpStatus.OK);
		else
			return new ResponseEntity("Unable to  GET",HttpStatus.BAD_GATEWAY);
		
	}
	
	@RequestMapping(value="/moderators/{mod_id}/polls/{poll_id}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Polls> pollViewWR(@PathVariable("mod_id") String mod_id, @PathVariable("poll_id") String poll_id) throws Exception{
		Polls p = null;
		boolean flag = false;
		if(poll_list!= null && !mod_id.equals("")){
			if(poll_list.size()>0){
				for(Polls forPoll:poll_list){
					System.out.println(forPoll);
					if(forPoll.getModerator_id().equals(mod_id)){
						if(forPoll.getId().equals(poll_id)){
							p = forPoll;
							flag=true; break;
						}
					}
				}
			}
			
		}
		if(flag)
			return new ResponseEntity<Polls>(p,HttpStatus.OK);
		else
			return new ResponseEntity("Unable to Find",HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value="/moderators/{mod_id}/polls",method=RequestMethod.GET)
	public @ResponseBody ArrayList<Polls> listPolls(@PathVariable("mod_id") String mod_id) throws Exception{
		ArrayList<Polls> mod_pollList = new ArrayList<Polls>();
		if(poll_list!=null && !mod_id.equals("")){
			if(poll_list.size()>0){
				for(Polls forPoll_mod:poll_list){
					if(forPoll_mod.getModerator_id().equals(mod_id)){
						mod_pollList.add(forPoll_mod);
					}
				}
			}
		}
		return mod_pollList;
	}

	@RequestMapping(value="/moderators/{mod_id}/polls/{poll_id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> delete_polls(@PathVariable String mod_id,@PathVariable String poll_id){
		boolean flag = false;
		if(poll_list!=null && poll_id !=null)
		for(int count=0; count<poll_list.size();count++){
			if(poll_list.get(count).getId().equals(poll_id)){
				poll_list.remove(count);
				flag = true;
			}
			
		}
		if(flag)
			return new ResponseEntity<String>("Poll Removed",HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/polls/{poll_id}", method=RequestMethod.PUT)
	public ResponseEntity<String> votePoll(@PathVariable String poll_id, @RequestParam int choice){
		boolean flag = false;
		if(poll_list!=null && poll_id!=null){
			for(int count=0; count< poll_list.size();count++){
				if(poll_list.get(count).getId().equals(poll_id)){
					poll_list.get(count).getResults()[choice]++;
					flag = true; break;
				}
			}
		}
		
	if(flag)
		return new ResponseEntity<String>("Vote Captured",HttpStatus.NO_CONTENT);
	else
		return new ResponseEntity<String>("Poll Not Available",HttpStatus.NO_CONTENT);
	
	}
	
	
	
	
}
