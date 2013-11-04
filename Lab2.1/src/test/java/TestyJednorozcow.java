import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import pl.domain.Jednorozec;
import pl.domain.Samochod;
import pl.service.ManagerJednorozcow;


public class TestyJednorozcow {
	public ManagerJednorozcow managerJednorozcow = new ManagerJednorozcow();
	public Jednorozec jednorozec1 = new Jednorozec("Michal",1991,2000);
	public Jednorozec jednorozec2 = new Jednorozec("Monika",1996,1500);
	public Jednorozec jednorozec3 = new Jednorozec("Rafal",2091,4500);
	public Samochod samochod1 = new Samochod("Mercedes",2009);
	public Samochod samochod2 = new Samochod("Tico",2004);
	public Samochod samochod3 = new Samochod("Fiat",2003);
	public final String imieJ = "Adam";
	public final int rokUrodzeniaJ = 1990;
	public final int wagaJ = 400;
	public final String nazwaS = "Lada";
	public final int rokProdukcjiS = 1985;
	
	
	@Before
	public void cleanAll(){
		managerJednorozcow.deleteAllJednorozec();
		managerJednorozcow.deleteAllSamochod();
	}
	
	@Test
	public void checkConnection(){
		assertNotNull(managerJednorozcow.getConnection());
	}
	
	@Test
	public void checkDeleteSamochody(){
		managerJednorozcow.deleteAllSamochod();
		assertEquals(0, managerJednorozcow.getSamochody().size());
	}
	
	@Test
	public void checkDeleteSamochodById(){
		managerJednorozcow.addSamochod(samochod1);
		managerJednorozcow.addSamochod(samochod2);
		Long id = managerJednorozcow.getSamochody().get(1).getId();
		
		assertEquals(1, managerJednorozcow.deleteSamochodById(id));
		assertNull(managerJednorozcow.getSamochodById(id));
	}
	
	@Test
	public void checkDeleteJednorozce(){
		managerJednorozcow.deleteAllJednorozec();
		assertEquals(0, managerJednorozcow.getJednorozce().size());
	}
	
	@Test
	public void checkDeleteJednorozecById(){
		managerJednorozcow.addJednorozec(jednorozec1);
		managerJednorozcow.addJednorozec(jednorozec2);
		Long id = managerJednorozcow.getJednorozce().get(1).getId();
		
		assertEquals(1, managerJednorozcow.deleteJednorozecById(id));
		assertNull(managerJednorozcow.getJednorozecById(id));
	}
	
	@Test
	public void checkDeleteJednorozecFromSamochod(){
		managerJednorozcow.addJednorozec(jednorozec1);
		managerJednorozcow.addSamochod(samochod1);
		Long id = managerJednorozcow.getSamochody().get(0).getId();
		
		assertEquals(1,managerJednorozcow.addSamochodToJednorozec(managerJednorozcow.getJednorozce().get(0).getId(),managerJednorozcow.getSamochody().get(0).getId()));
		assertEquals(1, managerJednorozcow.deleteJednorozecFromSamochod(id));
	}
	
	@Test
	public void addJednorozec(){
		Jednorozec j = new Jednorozec(imieJ, rokUrodzeniaJ, wagaJ);
		assertEquals(1, managerJednorozcow.addJednorozec(j));
		ArrayList<Jednorozec> jednorozce = managerJednorozcow.getJednorozce();
		
		assertEquals(imieJ, jednorozce.get(0).getImieJednorozca());
		assertEquals(rokUrodzeniaJ, jednorozce.get(0).getRokUrodzeniaJednorozca());
		assertEquals(wagaJ, jednorozce.get(0).getWagaJednorozca());	
	}
	
	@Test
	public void getJednorozecById(){
		assertEquals(1,managerJednorozcow.addJednorozec(jednorozec1));
		Long idJ = managerJednorozcow.getJednorozce().get(0).getId();
		assertNotNull(managerJednorozcow.getJednorozecById(idJ));
	}
	
	@Test
	public void addSamochod(){
		Samochod s = new Samochod(nazwaS, rokProdukcjiS);
		assertEquals(1, managerJednorozcow.addSamochod(s));
		ArrayList<Samochod> samochody = managerJednorozcow.getSamochody();
		
		assertEquals(nazwaS, samochody.get(0).getNazwa());
		assertEquals(rokProdukcjiS, samochody.get(0).getRokProdukcji());
	}
	
	@Test
	public void getSamochodById(){
		assertEquals(1,managerJednorozcow.addSamochod(samochod1));
		Long idJ = managerJednorozcow.getSamochody().get(0).getId();
		assertNotNull(managerJednorozcow.getSamochodById(idJ));
	}
	
	@Test
	public void addSamochodToJednorozec(){
		Samochod s = new Samochod(nazwaS, rokProdukcjiS);
		Jednorozec j = new Jednorozec(imieJ, rokUrodzeniaJ, wagaJ);
		assertEquals(1, managerJednorozcow.addSamochod(s));
		assertEquals(1, managerJednorozcow.addJednorozec(j));
		
		assertEquals(1,managerJednorozcow.addSamochodToJednorozec(managerJednorozcow.getJednorozce().get(0).getId(),managerJednorozcow.getSamochody().get(0).getId()));
	}
	
	@Test
	public void getAllSamochodyFromJednorozec(){
		managerJednorozcow.addJednorozec(jednorozec1);
		managerJednorozcow.addJednorozec(jednorozec2);
		Long idJ0 = managerJednorozcow.getJednorozce().get(0).getId();
		Long idJ1 = managerJednorozcow.getJednorozce().get(1).getId();
		managerJednorozcow.addSamochod(samochod1);
		managerJednorozcow.addSamochod(samochod2);
		managerJednorozcow.addSamochod(samochod3);
		
		assertEquals(1,managerJednorozcow.addSamochodToJednorozec(managerJednorozcow.getJednorozce().get(0).getId(),managerJednorozcow.getSamochody().get(0).getId()));
		assertEquals(1,managerJednorozcow.addSamochodToJednorozec(managerJednorozcow.getJednorozce().get(1).getId(),managerJednorozcow.getSamochody().get(1).getId()));
		assertEquals(1,managerJednorozcow.addSamochodToJednorozec(managerJednorozcow.getJednorozce().get(0).getId(),managerJednorozcow.getSamochody().get(2).getId()));
	
		assertEquals(2, managerJednorozcow.getSamochodyFromJednorozec(idJ0).size());
		assertEquals(1, managerJednorozcow.getSamochodyFromJednorozec(idJ1).size());
	}
	
	@Test
	public void getAllJednorozce(){
		managerJednorozcow.addJednorozec(jednorozec1);
		managerJednorozcow.addJednorozec(jednorozec2);
		managerJednorozcow.addJednorozec(jednorozec3);
		
		assertEquals(3, managerJednorozcow.getJednorozce().size());
	}
	
	@Test
	public void getAllSamochody(){
		managerJednorozcow.addSamochod(samochod1);
		managerJednorozcow.addSamochod(samochod2);
		managerJednorozcow.addSamochod(samochod3);
		
		assertEquals(3, managerJednorozcow.getSamochody().size());
	}
	
}
