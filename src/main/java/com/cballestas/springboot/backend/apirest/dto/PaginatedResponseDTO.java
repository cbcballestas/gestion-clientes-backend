package com.cballestas.springboot.backend.apirest.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedResponseDTO<T> {
	
	List<T> data;
	boolean isLast;
	long totalElements;
	int totalPages;
	int size;
	int number;
	boolean isFirst;
	boolean isEmpty;
	

}
