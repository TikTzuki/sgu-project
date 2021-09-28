package com.tourism.BUS;

import com.tourism.DAL.EmployeeRepository;
import com.tourism.DAL.PositionRepository;
import com.tourism.DAL.TourPositionRepository;
import com.tourism.DTO.TourPosition;

public class TourPositionController {
	TourPositionRepository tourPositionRepository;
	EmployeeRepository empRepository;
	PositionRepository positionRepository;
	
	public TourPositionController() {
		tourPositionRepository = new TourPositionRepository();
		empRepository = new EmployeeRepository();
		positionRepository = new PositionRepository();
	}
	
	public TourPosition initFromTouristGroupByEmployeeAndPosition(Long touristGroupId,Long empId, Long positionId) {
		TourPosition TP = new TourPosition();
		TP.setTouristGroupId(touristGroupId);
		TP.setEmployeeId(empId);
		TP.setPositionId(positionId);
		TP.setEmployee(empRepository.findById(empId).orElse(null));
		TP.setPosition(positionRepository.findById(positionId).orElse(null));
		return TP;
	}
}
