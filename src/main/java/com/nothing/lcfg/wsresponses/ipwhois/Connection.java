package com.nothing.lcfg.wsresponses.ipwhois;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Connection{
	private int asn;
	private String org;
	private String isp;
	private String domain;
}
