package com.tourism.BUS;

import java.util.List;

import com.tourism.DAL.TouristGroupCostRepository;
import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DTO.TouristGroupCost;

public class TouristGroupCostController {
	TouristGroupCostRepository touristGroupCostRepository;
	TouristGroupRepository touristGroupRepository;
	
	public TouristGroupCostController() {
		touristGroupCostRepository = new TouristGroupCostRepository();
		touristGroupRepository = new TouristGroupRepository();
	}
	
	public List<TouristGroupCost> getAll(){
		List<TouristGroupCost> touristGroupCosts;
		touristGroupCosts = touristGroupCostRepository.findAll();
		return touristGroupCosts;
	}
	
	public TouristGroupCost getById(Long id) {
		TouristGroupCost touristGroupCost = new TouristGroupCost();
		touristGroupCost = touristGroupCostRepository.findById(id).get();
		return touristGroupCost;
	}
	
	public TouristGroupCost create(TouristGroupCost cost) {
		cost.setId(null);
		return touristGroupCostRepository.save(cost);
	}
	
	public TouristGroupCost modify(TouristGroupCost cost) {
		return touristGroupCostRepository.save(cost);
	}
}
