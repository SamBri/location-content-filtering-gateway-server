package com.nothing.lcfg.wsresponses.ipwhois;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Currency{
	private String name;
	private String code;
	private String symbol;
	private String plural;
	private int exchange_rate;
}
