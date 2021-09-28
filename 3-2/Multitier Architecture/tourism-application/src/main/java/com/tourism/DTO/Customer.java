package com.tourism.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Customer
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
	protected Long id;
	@NonNull
	protected String name;
	@NonNull
	protected String identityCard;
	@NonNull
	protected String address1;
	@NonNull
	protected String address2;
	@NonNull
	protected String address3;
	@NonNull
	protected String street;
	@NonNull
	protected String gender;
	@NonNull
	protected String phoneNumber;
	protected List<TouristGroup> touristGroups;

  public static void main(String[] args) {
    Customer c = new Customer();
    c.getName();
  }
}
