package com.yoru.currency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
	@Id
	String code;
	
	@Column
	String name;
	
	@Column
	Double rate;
//	
//	@LastModifiedDate
//	@Column(nullable=false)
//	Date updateTime = new Date();

}
