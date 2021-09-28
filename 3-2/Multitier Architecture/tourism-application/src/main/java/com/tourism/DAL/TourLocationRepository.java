package com.tourism.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.TourLocation;

public class TourLocationRepository implements Repositories<TourLocation, Long> {
	Connector connector = new MysqlConnector();
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public TourLocation save(TourLocation entity) {
		List<TourLocation> tourLocations = new ArrayList<TourLocation>();
		tourLocations.add(entity);
		return saveAll(tourLocations).get(0);
	}

	@Override
	public List<TourLocation> saveAll(Iterable<TourLocation> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
//			if(e.getType() !=null &&  e.getType().getId()!=null)
//				e.setTypeId(e.getType().getId());
//			if (findById(e.getTour_id()).isPresent()) {
//				StringBuilder updateQuery = new StringBuilder("UPDATE tour_location SET ");
//				updateQuery.append("tour_id = \"" + e.getTour_id() + "\", ");
//				updateQuery.append("location_id = \"" + e.getLocation_id() + "\" ");
//				updateQuery.append("WHERE tour_id = \"" + e.getTour_id() + "\" ;");
//				logger.info(updateQuery.toString());
//				this.connector.executeUpdate(updateQuery.toString());
//			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO `tour_location` (`tour_id`, `location_id`) VALUES");
				insertQuery.append("(\""+e.getTour_id()+ "\", ");
				insertQuery.append("\"" + e.getLocation_id() +"\"); ");
				this.connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM tour_location ORDER BY `tour_id` DESC LIMIT 1");
				try {
					while (returnedResultSet != null && returnedResultSet.next()) {
						e.setTour_id(Long.valueOf(returnedResultSet.getString("tour_id")));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//			}
			
			ids.add(e.getTour_id());
		});
		return findAllById(ids);
	}
	

	@Override
	public Optional<TourLocation> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<TourLocation> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}

	@Override
	public List<TourLocation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TourLocation> findAllById(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<TourLocation>();
		List<TourLocation> tourLocations = new ArrayList<TourLocation>();
		StringBuilder query = new StringBuilder("SELECT * FROM tour_location WHERE ");
		ids.forEach(id -> {
			query.append("tour_id = \"" + id + "\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0,query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(TourLocation entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends TourLocation> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<TourLocation> extractResultSet(ResultSet rs){
		List<TourLocation> tourLocations = new ArrayList<TourLocation>();
		try {
			while (rs!=null && rs.next()) {
				TourLocation tourLocation = new TourLocation();
				tourLocation.setTour_id(Long.valueOf(rs.getLong("tour_id")));
				tourLocation.setLocation_id(Long.valueOf(rs.getString("location_id")));
				
				tourLocations.add(tourLocation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tourLocations;
	}
	
	public void deleteAllById(Long tour_id) {
		this.connector.executeUpdate("delete from tour_location where tour_id ='" + tour_id + "'");
	}

}
