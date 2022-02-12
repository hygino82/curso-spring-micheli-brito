package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

	@Autowired
	private ParkingSpotService parkingSpotService;

	@PostMapping
	@ApiOperation(value = "Salvar uma vaga de estacionamento")
	@ApiResponses({@ApiResponse(code = 201, message = "Vaga de estacionamento salva com sucesso!")})
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

		if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Licence Plate car is already in use!");
		}

		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
		}

		if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment() , parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflict: Parking Spot is already registered for this apartment/block!");
		}

		var parkingspotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingspotModel);// faz a copia dos atributos de dto para model
		parkingspotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingspotModel));
	}

	@GetMapping
	@ApiOperation(value = "Listar todas as vagas de estacionamento")
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot() {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Procurar uma vaga de estacionamento usando Id")
	public ResponseEntity<Object> findParkingSpotById(@PathVariable(value = "id") UUID id) {
		Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
		if (!parkingSpotOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Park spot no found!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotOptional.get());
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar uma vaga de estacionamento usando Id")
	public ResponseEntity<Object> deleteParkingspotById(@PathVariable(value = "id") UUID id) {
		Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
		if (!parkingSpotOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Park spot no found!");
		}
		parkingSpotService.delete(parkingSpotOptional.get());

		return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully");
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar uma vaga de estacionamento usando Id")
	public ResponseEntity<Object> updateParkingspotById(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
		Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
		if (!parkingSpotOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Park spot no found!");
		}

		var parkingSpotModel = new ParkingSpotModel(); 
	
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);//copia os dados de um objeto para o outro
		parkingSpotModel.setId(parkingSpotOptional.get().getId());
		parkingSpotModel.setRegistrationDate(parkingSpotOptional.get().getRegistrationDate());

		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
	}

}
