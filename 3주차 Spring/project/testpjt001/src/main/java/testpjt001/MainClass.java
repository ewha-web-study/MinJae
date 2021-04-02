package testpjt001;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml"); 
		TransportationWalk transportation = ctx.getBean("tWalk", TransportationWalk.class); 
		transportation.move(); 
		
		ctx.close(); 
		
	}
}
