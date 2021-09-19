package com.eventoapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evento {
	private String nome;
	private String local;
	private String data;
	private String horario;
}
