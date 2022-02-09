package com.api.parkingcontrol.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotDto {

	@NotBlank
	private String parkingSpotNumber;

	@NotBlank
	@Size(max = 7)
	private String licensePlateCar;

	@NotBlank
	private String brandCar;

	@NotBlank
	private String modelCar;

	@NotBlank
	private String colorCar;

	@NotBlank
	private String responsibileName;

	@NotBlank
	private String apartment;

	@NotBlank
	private String block;
}
