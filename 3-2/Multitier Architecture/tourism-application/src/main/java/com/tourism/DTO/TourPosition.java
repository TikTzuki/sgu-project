package com.tourism.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * RoleTour
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourPosition {
	protected Long id;
	protected Long touristGroupId;
	protected Long employeeId;
	protected Long positionId;
	protected TouristGroup touristGroup; 
	protected Employee employee;
	protected Position position;
}
