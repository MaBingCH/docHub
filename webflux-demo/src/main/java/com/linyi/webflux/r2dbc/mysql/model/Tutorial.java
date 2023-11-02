package com.linyi.webflux.r2dbc.mysql.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tutorial {
	  
	@Id
	private int id;
	private String title;
	private String description;
	private boolean published;
	
	public Tutorial(String title, String description, boolean pub) {
		this.title = title;
		this.description = description;
		this.published = pub;
	}
	
}
