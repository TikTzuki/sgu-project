package com.tourism.BUS;

import java.util.ArrayList;
import java.util.List;

import com.tourism.DAL.PositionRepository;
import com.tourism.DTO.Position;

public class PositionController {
	PositionRepository positionRepository;
	
	public PositionController() {
		positionRepository = new PositionRepository();
	}
	
	public List<Position> getAll(){
		List<Position> positions = new ArrayList<Position>();
		positions = positionRepository.findAll();
		return positions;
	}
}
