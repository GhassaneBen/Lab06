package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		List<Rilevamento>rilevamentoML=new ArrayList<Rilevamento>();
		
		for(Rilevamento r:this.getAllRilevamenti()) {
			
           int Mese=r.getData().getMonth();
           
           if(mese==Mese && localita==r.getLocalita()) {
        	   rilevamentoML.add(r);
        	   
           }
			
			
		}
		
		

		return rilevamentoML;
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		
		List<Rilevamento> rilevamentoML2=this.getAllRilevamentiLocalitaMese(mese, localita);
		
		double sum=0.0;
		
		for(Rilevamento r:rilevamentoML2) {
			
			sum+=(double)r.getUmidita();
			
		}
		

		return sum/((double)rilevamentoML2.size());
	}
	
	public List<String> getAllLocalita(){
		
		List<String>allLocalita=new ArrayList<String>();
		for(Rilevamento r:this.getAllRilevamenti()) {
			
			allLocalita.add(r.getLocalita());
			
			
		}
		return allLocalita;
		
	}

}
