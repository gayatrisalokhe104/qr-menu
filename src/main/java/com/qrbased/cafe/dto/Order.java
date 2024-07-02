package com.qrbased.cafe.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name ="Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("tokenId")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	private String  customerName;
	private long phone;
	private double totalAmount;
	private boolean paymentStatus;
	private int tableNumber;
	private String clientSecret;
	private boolean complete;
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderFood> orderFoods=new ArrayList<OrderFood>();
	private String paymentIntentId;
	private LocalDateTime localDateTime;
	@Transient
	private String tokenId;
	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	@PreRemove
	private void preRemove() {
		if(orderFoods!=null && !orderFoods.isEmpty()) {
			orderFoods.clear();
		}
	}
}
