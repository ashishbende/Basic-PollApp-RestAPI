package vote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;*/

/*
 *  @author Ashish Bende
 */

@RestController
@RequestMapping("/api/v1")
public class ModeratorsController{
	
	private final AtomicLong counter = new AtomicLong(123455);
	
	/*DateTime dt = new DateTime();
	DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
	String currentTime = fmt.print(dt);*/
	
	DateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	String currentTime = dt.format(new Date());
	
	ArrayList<Moderators> modlist = new ArrayList<Moderators>();
	
	@RequestMapping(value="/moderators", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Moderators moderatorsCreate(@RequestBody @Validated(Checker.CheckerAll.class)Moderators moderators){
		
		String name = moderators.getName();
		String email = moderators.getEmail();
		String password = moderators.getPassword();	
		
		Moderators mods = new Moderators(String.valueOf(counter.incrementAndGet()),name,email,password,currentTime);
		modlist.add(mods);
		return mods;
		
	}
	
	@RequestMapping(value="/moderators/{id}", method=RequestMethod.GET)
	public Moderators moderatorsView(@PathVariable("id") String id){
		boolean flag = false;
		Moderators tempMod1 = null;
		for(Moderators forMods:modlist){
			if (forMods.getId().equals(id)){
			    
			    flag = true;
			    tempMod1 = forMods;
			    break;
			}
			    
		}
		
		if(flag)
			return tempMod1;
		else
		    return null;
	}
	
	@RequestMapping(value="/moderators/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Moderators> moderatorsUpdate(@PathVariable("id") String id,@RequestBody @Validated(Checker.CheckerFields.class)Moderators moderators){
		
		boolean flag = false;
		Moderators tempMod2 = null;
		
		for(Moderators forMods:modlist){
			if (forMods.getId().equals(id) ){
				tempMod2 = forMods;
				flag = true;
				break;
			}
		}
		
		if(flag){
			
			String email=moderators.getEmail();
			String password=moderators.getPassword();
			
			tempMod2.setEmail(email);
			tempMod2.setPassword(password);
			
			return new ResponseEntity<Moderators>(tempMod2,HttpStatus.OK);
		}
		else
			return new ResponseEntity("Unable to GET", HttpStatus.BAD_GATEWAY);
	}
	
	
	
}

