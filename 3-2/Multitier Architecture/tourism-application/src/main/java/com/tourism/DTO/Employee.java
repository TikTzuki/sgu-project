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
 * Employee
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

	protected Long id;
	protected String name;
	protected String identityCard;
	protected String address1;
	protected String address2;
	protected String address3;
	protected String street;
	protected String gender;
	protected String phoneNumber;
	protected String status;
	protected List<TourPosition> tourPositions;
	protected List<TouristGroup> touristGroups;
	public static void main(String[] args) {
	}
}
