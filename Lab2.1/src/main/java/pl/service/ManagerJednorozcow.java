package pl.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.domain.Jednorozec;
import pl.domain.Samochod;

public class ManagerJednorozcow {
	
	public final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private Connection connection;

	private PreparedStatement addSamochod;
	private PreparedStatement deleteAllSamochod;
	private PreparedStatement deleteSamochodById;
	private PreparedStatement addJednorozec;
	private PreparedStatement deleteAllJednorozec;
	private PreparedStatement deleteJednorozecById;
	private PreparedStatement jednorozecById;
	private PreparedStatement samochodById;
	private PreparedStatement addJednorozecToSamochod;
	private PreparedStatement getAllSamochodyFromJednorozec;
	private PreparedStatement deleteJednorozecFromSamochod;
	private boolean samochodTableExists = false;
	private boolean jednorozecTableExists = false;
	private String createTabelaJednorozec = "Create TABLE Jednorozec (id bigint IDENTITY, imieJednorozca varchar(25), rokUrodzeniaJednorozca INTEGER, wagaJednorozca INTEGER)";
	private String createTabelaSamochod = "CREATE TABLE Samochod (id bigint IDENTITY, nazwa varchar(25), rokProdukcji INTEGER, idJednorozec bigint)";
	
	public ManagerJednorozcow(){
		ConnectToDB();
	}
	
	void ConnectToDB(){
		try {
			connection = DriverManager.getConnection(URL);
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			while(rs.next()){
				if("Jednorozec".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
					jednorozecTableExists = true;
				}
				else if("Samochod".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
					samochodTableExists = true;
				}
			}
			
			if(!jednorozecTableExists){
				connection.createStatement().execute(createTabelaJednorozec);
			}
			
			if(!samochodTableExists){
				connection.createStatement().execute(createTabelaSamochod);
			}
			
			addJednorozec = connection.prepareStatement("INSERT INTO JEDNOROZEC (imieJednorozca, rokUrodzeniaJednorozca, wagaJednorozca) VALUES(?,?,?)");
			deleteAllJednorozec = connection.prepareStatement("DELETE FROM JEDNOROZEC");
			addSamochod = connection.prepareStatement("INSERT INTO SAMOCHOD (nazwa, rokProdukcji) VALUES(?,?)");
			deleteAllSamochod = connection.prepareStatement("DELETE FROM SAMOCHOD");
			jednorozecById = connection.prepareStatement("SELECT id, imieJednorozca, rokUrodzeniaJednorozca, wagaJednorozca FROM JEDNOROZEC where id=?");
			samochodById = connection.prepareStatement("SELECT id, nazwa, rokProdukcji FROM SAMOCHOD where id=?");
			addJednorozecToSamochod = connection.prepareStatement("UPDATE SAMOCHOD SET idJednorozec=? WHERE id=?");
			getAllSamochodyFromJednorozec = connection.prepareStatement("SELECT id, nazwa, rokProdukcji FROM SAMOCHOD where idJednorozec=?");
			deleteJednorozecFromSamochod = connection.prepareStatement("UPDATE SAMOCHOD SET idJednorozec=null WHERE id=?");
			deleteJednorozecById = connection.prepareStatement("DELETE FROM JEDNOROZEC WHERE id=?");
			deleteSamochodById = connection.prepareStatement("DELETE FROM SAMOCHOD WHERE id=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addJednorozec(Jednorozec jednorozec){
		int count = 0;
		try{
			addJednorozec.setString(1, jednorozec.getImieJednorozca());
			addJednorozec.setInt(2, jednorozec.getRokUrodzeniaJednorozca());
			addJednorozec.setInt(3, jednorozec.getWagaJednorozca());
			count = addJednorozec.executeUpdate();
		}catch(SQLException sqE){
			sqE.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<Jednorozec> getJednorozce(){
		ArrayList<Jednorozec> jednorozce = new ArrayList<Jednorozec>();
		try {
			ResultSet rs = connection.prepareStatement("SELECT id, imieJednorozca, rokUrodzeniaJednorozca, wagaJednorozca FROM JEDNOROZEC").executeQuery();
			while(rs.next()){
				Jednorozec jednorozec = new Jednorozec(rs.getString("imieJednorozca"), rs.getInt("rokUrodzeniaJednorozca"), rs.getInt("wagaJednorozca"));
				jednorozec.setId(rs.getLong("id"));
				jednorozce.add(jednorozec);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jednorozce;
	}
	
    public Jednorozec getJednorozecById(Long id){
        Jednorozec jednorozec = null;
        try {
                jednorozecById.setLong(1, id);
                ResultSet rs = jednorozecById.executeQuery();
                while(rs.next()){
                        if(rs.getLong("id")==id){
                                jednorozec = new Jednorozec(rs.getString("imieJednorozca"), rs.getInt("rokUrodzeniaJednorozca"), rs.getInt("wagaJednorozca"));
                                jednorozec.setId(rs.getLong("id"));
                        }    
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return jednorozec;
    }
	
	public int addSamochod(Samochod samochod){
		int count = 0;
		try{
			addSamochod.setString(1, samochod.getNazwa());
			addSamochod.setInt(2, samochod.getRokProdukcji());
			count = addSamochod.executeUpdate();
		}catch(SQLException sqE){
			sqE.printStackTrace();
		}
		return count;
	}
	
    public Samochod getSamochodById(Long id){
        Samochod samochod = null;
        try {
                samochodById.setLong(1, id);
                ResultSet rs = samochodById.executeQuery();
                while(rs.next()){
                        if(rs.getLong("id")==id){
                                samochod = new Samochod(rs.getString("nazwa"), rs.getInt("rokProdukcji"));
                                samochod.setId(rs.getLong("id"));
                        }    
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return samochod;
    }
    
	public ArrayList<Samochod> getSamochody(){
		ArrayList<Samochod> samochody = new ArrayList<Samochod>();
		try {
			ResultSet rs = connection.prepareStatement("SELECT id, nazwa, rokProdukcji,idJednorozec FROM SAMOCHOD").executeQuery();
			while(rs.next()){
				Samochod samochod = new Samochod(rs.getString("nazwa"), rs.getInt("rokProdukcji"));
				samochod.setId(rs.getLong("id"));
				samochod.setIdJednorozec(rs.getLong("idJednorozec"));
				samochody.add(samochod);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return samochody;
	}
	
	public int addSamochodToJednorozec(Long idJednorozec, Long idSamochod){
		int count = 0;
			try {
				addJednorozecToSamochod.setLong(1, idJednorozec);
				addJednorozecToSamochod.setLong(2, idSamochod);
				count = addJednorozecToSamochod.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return count;
	}
	
	public ArrayList<Samochod> getSamochodyFromJednorozec(Long id){
		ArrayList<Samochod> samochody = new ArrayList<Samochod>();
		try {
			getAllSamochodyFromJednorozec.setLong(1, id);
			ResultSet rs = getAllSamochodyFromJednorozec.executeQuery();
			
			while(rs.next()){
				Samochod samochod = new Samochod(rs.getString("nazwa"), rs.getInt("rokProdukcji"));
				samochod.setId(rs.getLong("id"));
				samochod.setIdJednorozec(rs.getLong(3));
				samochody.add(samochod);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return samochody;
	}
	
	public void deleteAllSamochod(){
		try {
			deleteAllSamochod.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllJednorozec(){
		try {
			deleteAllJednorozec.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int deleteJednorozecFromSamochod(Long id){
		int count = 0;
			try {
				deleteJednorozecFromSamochod.setLong(1, id);
				count = deleteJednorozecFromSamochod.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return count;
	}
	
	public int deleteJednorozecById(Long id){
		int count = 0;
			try {
				deleteJednorozecById.setLong(1, id);
				count = deleteJednorozecById.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return count;
	}
	
	public int deleteSamochodById(Long id){
		int count = 0;
		
			try {
				deleteSamochodById.setLong(1, id);
				count = deleteSamochodById.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return count;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
